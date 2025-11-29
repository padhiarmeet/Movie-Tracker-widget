import 'dart:convert';
import 'package:home_widget/home_widget.dart';
import '../models/movie.dart';
import '../db/database_helper.dart';

class WidgetSync {
  static const String appGroupId = 'group.movie_tracker';
  static const String androidWidgetName = 'MovieWidgetProvider';

  static Future<void> updateWidget() async {
    final movies = await DatabaseHelper.instance.readAllMovies();
    
    // Serialize the list of movies to JSON
    final moviesJson = jsonEncode(
      movies.map((movie) => {
        'name': movie.name,
        'releaseDate': movie.releaseDate.toIso8601String(),
        'imagePath': movie.imagePath,
        'cardColor': movie.cardColor,
      }).toList(),
    );

    // Save to HomeWidget storage
    await HomeWidget.saveWidgetData<String>('movie_list', moviesJson);
    
    // Update the widget
    await HomeWidget.updateWidget(
      name: androidWidgetName,
      qualifiedAndroidName: 'com.example.movie_tracker.MovieWidgetProvider',
    );
  }
}
