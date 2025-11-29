package com.example.movie_tracker

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import es.antonborri.home_widget.HomeWidgetPlugin
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class MovieWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return MovieRemoteViewsFactory(this.applicationContext)
    }
}

class MovieRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private var movies: JSONArray = JSONArray()
    private val TAG = "MovieWidgetService"

    override fun onCreate() {
        Log.d(TAG, "onCreate called")
    }

    override fun onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged called")
        try {
            val widgetData = HomeWidgetPlugin.getData(context)
            val moviesJson = widgetData.getString("movie_list", "[]")
            Log.d(TAG, "Movies JSON: $moviesJson")
            movies = JSONArray(moviesJson)
            Log.d(TAG, "Loaded ${movies.length()} movies")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading movies", e)
            movies = JSONArray()
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy called")
    }

    override fun getCount(): Int {
        val count = movies.length()
        Log.d(TAG, "getCount: $count")
        return count
    }

    override fun getViewAt(position: Int): RemoteViews {
        Log.d(TAG, "getViewAt position: $position")
        val views = RemoteViews(context.packageName, R.layout.widget_item)
        
        try {
            if (position >= movies.length()) {
                Log.w(TAG, "Position $position >= movies length ${movies.length()}")
                return views
            }
            
            val movie = movies.getJSONObject(position)
            val name = movie.optString("name", "Unknown Movie")
            val releaseDate = movie.optString("releaseDate", "")
            val imagePath = if (movie.has("imagePath") && !movie.isNull("imagePath")) {
                movie.optString("imagePath", null)
            } else {
                null
            }
            val cardColorHex = if (movie.has("cardColor") && !movie.isNull("cardColor")) {
                movie.optString("cardColor", null)
            } else {
                null
            }
            
            Log.d(TAG, "Movie: $name, Date: $releaseDate, Image: $imagePath, Color: $cardColorHex")
            
            // Apply card color if available
            if (cardColorHex != null && cardColorHex.startsWith("#")) {
                try {
                    val color = android.graphics.Color.parseColor(cardColorHex)
                    views.setInt(R.id.card_container, "setBackgroundColor", color)
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing color: $cardColorHex", e)
                    // Keep default white background
                }
            }
            
            // Format date
            val dateDisplay = if (releaseDate.isNotEmpty() && releaseDate.length >= 10) {
                releaseDate.substring(0, 10)
            } else {
                "Unknown date"
            }

            views.setTextViewText(R.id.movie_name, name)
            views.setTextViewText(R.id.movie_date, "Release: $dateDisplay")
            
            // Load and set image
            var imageSet = false
            if (!imagePath.isNullOrEmpty()) {
                try {
                    val bitmap = loadScaledBitmap(imagePath, 240, 240)
                    if (bitmap != null) {
                        views.setImageViewBitmap(R.id.movie_image, bitmap)
                        imageSet = true
                        Log.d(TAG, "Image loaded successfully for $name")
                    } else {
                        Log.w(TAG, "Bitmap is null for path: $imagePath")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error loading image for $name", e)
                }
            }
            
            if (!imageSet) {
                // Set default icon
                views.setImageViewResource(R.id.movie_image, android.R.drawable.ic_menu_gallery)
                Log.d(TAG, "Using default icon for $name")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in getViewAt", e)
        }

        return views
    }

    private fun loadScaledBitmap(path: String, maxWidth: Int, maxHeight: Int): Bitmap? {
        return try {
            val file = File(path)
            if (!file.exists()) {
                Log.w(TAG, "File does not exist: $path")
                return null
            }
            
            Log.d(TAG, "Loading bitmap from: $path")
            
            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)
            
            if (options.outWidth <= 0 || options.outHeight <= 0) {
                Log.w(TAG, "Invalid image dimensions")
                return null
            }
            
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.RGB_565 // Use less memory
            
            val bitmap = BitmapFactory.decodeFile(path, options)
            Log.d(TAG, "Bitmap loaded: ${bitmap != null}, size: ${bitmap?.width}x${bitmap?.height}")
            bitmap
        } catch (e: Exception) {
            Log.e(TAG, "Error loading bitmap from $path", e)
            null
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    override fun getLoadingView(): RemoteViews? {
        Log.d(TAG, "getLoadingView called")
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}
