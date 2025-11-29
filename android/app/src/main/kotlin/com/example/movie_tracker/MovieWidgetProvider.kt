package com.example.movie_tracker

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetPlugin

class MovieWidgetProvider : AppWidgetProvider() {
    private val TAG = "MovieWidgetProvider"
    
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d(TAG, "onUpdate called for ${appWidgetIds.size} widgets")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        
        when (intent.action) {
            ACTION_REFRESH -> {
                Log.d(TAG, "Refresh button clicked")
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(
                    ComponentName(context, MovieWidgetProvider::class.java)
                )
                for (appWidgetId in appWidgetIds) {
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_grid)
                }
            }
            ACTION_ADD_MOVIE -> {
                Log.d(TAG, "Add button clicked")
                val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                launchIntent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                context.startActivity(launchIntent)
            }
        }
    }

    companion object {
        const val ACTION_REFRESH = "com.example.movie_tracker.ACTION_REFRESH"
        const val ACTION_ADD_MOVIE = "com.example.movie_tracker.ACTION_ADD_MOVIE"
        
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val TAG = "MovieWidgetProvider"
            Log.d(TAG, "updateAppWidget called for widget $appWidgetId")
            
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            
            // Set up the intent that starts the MovieWidgetService
            val intent = Intent(context, MovieWidgetService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }
            
            // Set up the RemoteViews object to use a RemoteViews adapter
            views.setRemoteAdapter(R.id.widget_grid, intent)
            views.setEmptyView(R.id.widget_grid, R.id.empty_view)
            
            // Refresh button intent
            val refreshIntent = Intent(context, MovieWidgetProvider::class.java).apply {
                action = ACTION_REFRESH
            }
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context, 0, refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.refresh_button, refreshPendingIntent)
            
            // Add movie button intent
            val addIntent = Intent(context, MovieWidgetProvider::class.java).apply {
                action = ACTION_ADD_MOVIE
            }
            val addPendingIntent = PendingIntent.getBroadcast(
                context, 1, addIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.add_button, addPendingIntent)

            // Notify the widget that the data has changed
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_grid)
            appWidgetManager.updateAppWidget(appWidgetId, views)
            
            Log.d(TAG, "Widget $appWidgetId updated")
        }
        
        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, MovieWidgetProvider::class.java)
            )
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }
}
