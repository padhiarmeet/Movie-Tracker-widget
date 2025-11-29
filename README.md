# 🎬 Movie Tracker

A modern, neobrutalist-styled Flutter application for tracking movie releases with a customizable Android home screen widget. Never miss a movie release date again!

![Flutter](https://img.shields.io/badge/Flutter-3.8.1-02569B?logo=flutter&logoColor=white)
![Dart](https://img.shields.io/badge/Dart-3.8.1-0175C2?logo=dart&logoColor=white)
![Android](https://img.shields.io/badge/Android-Widget-3DDC84?logo=android&logoColor=white)

## ✨ Features

### 📱 Core Functionality
- **Movie Management**: Add, view, and delete your anticipated movies
- **Custom Posters**: Upload movie posters from your gallery
- **Release Date Tracking**: Set and track precise release dates
- **Sorting Options**: Sort your movie list by:
  - Date Added (newest/oldest)
  - Release Date (soonest/latest)
  - Movie Name (A-Z/Z-A)

### 🏠 Android Home Screen Widget
- **Live Widget**: Display your tracked movies directly on your home screen
- **Customizable Colors**: Choose from 8 vibrant colors for each movie card in the widget
- **Quick Actions**: 
  - Refresh button to update widget data
  - Add new movie button for quick access
- **Auto-sync**: Widget automatically updates when movies are added, edited, or deleted
- **Beautiful Design**: Neobrutalist aesthetic with bold borders and vibrant colors

### 🎨 Design Philosophy
- **Neobrutalist Style**: Bold black borders, vibrant colors, and strong visual hierarchy
- **Premium UI/UX**: Modern, clean interface with smooth animations
- **Custom Color Palette**: Hand-picked colors including:
  - Neo Green (`#BAFCA2`)
  - Sky Blue (`#87CEEB`)
  - Soft Pink (`#FFC0CB`)
  - Coral Red (`#FFA07A`)
  - Purple (`#C4A1FF`)
  - Orange (`#FDBB58`)
  - Yellow (`#FDFD96`)
  - Powder Blue (`#B0E0E6`)

## 📸 Screenshots

*Coming soon - Add screenshots of your app here*

## 🚀 Getting Started

### Prerequisites

- Flutter SDK (>=3.8.1)
- Android Studio or VS Code with Flutter extensions
- An Android device or emulator for testing the widget

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/movie_tracker.git
   cd movie_tracker
   ```

2. **Install dependencies**
   ```bash
   flutter pub get
   ```

3. **Run the app**
   ```bash
   flutter run
   ```

### Widget Setup

After installing the app:
1. Long-press on your Android home screen
2. Tap "Widgets"
3. Find "Movie Tracker" widget
4. Drag it to your home screen
5. Your tracked movies will appear automatically!

## 🏗️ Architecture

### Project Structure

```
lib/
├── db/
│   └── database_helper.dart     # SQLite database management
├── models/
│   └── movie.dart                # Movie data model
├── screens/
│   ├── home_screen.dart          # Main movie list screen
│   └── add_movie_screen.dart     # Add/edit movie screen
├── utils/
│   └── widget_sync.dart          # Widget synchronization utilities
└── main.dart                     # App entry point

android/
└── app/src/main/kotlin/com/example/movie_tracker/
    ├── MainActivity.kt           # Main Android activity
    ├── MovieWidgetProvider.kt    # Widget provider
    └── MovieWidgetService.kt     # Widget data service
```

### Tech Stack

- **Framework**: Flutter 3.8.1
- **Language**: Dart 3.8.1
- **Database**: SQLite (via `sqflite` package)
- **Widget Integration**: `home_widget` package
- **Image Handling**: `image_picker` package
- **Date Formatting**: `intl` package

## 📦 Dependencies

```yaml
dependencies:
  flutter:
    sdk: flutter
  cupertino_icons: ^1.0.8
  sqflite: ^2.4.2
  path: ^1.9.1
  home_widget: ^0.8.1
  intl: ^0.20.2
  image_picker: ^1.2.1
  path_provider: ^2.1.5
```

## 🎯 Usage

### Adding a Movie

1. Tap the "+" button on the home screen
2. Upload a movie poster (optional)
3. Enter the movie name
4. Select the release date
5. Choose a widget card color
6. Tap "SAVE MOVIE"

### Sorting Movies

Tap the sort icon in the app bar to choose from:
- Date Added (Newest First / Oldest First)
- Release Date (Soonest First / Latest First)
- Name (A-Z / Z-A)

### Deleting a Movie

Tap the delete icon on any movie card in the home screen.

### Widget Customization

The widget automatically displays:
- Movie poster (if provided)
- Movie name
- Days until release
- Custom background color (chosen during movie creation)

## 🛠️ Development

### Building for Release

```bash
flutter build apk --release
```

### Running Tests

```bash
flutter test
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 Future Enhancements

- [ ] iOS widget support
- [ ] Cloud sync across devices
- [ ] Movie search integration with TMDB API
- [ ] Notifications for upcoming releases
- [ ] Dark mode support
- [ ] Export movies as CSV
- [ ] Share movie lists with friends
- [ ] Watch history tracking

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## 🙏 Acknowledgments

- Flutter team for the amazing framework
- Neobrutalist design community for inspiration
- Icons by Material Design Icons

## 📞 Support

If you encounter any issues or have questions, please [open an issue](https://github.com/yourusername/movie_tracker/issues).

---

Made with ❤️ and Flutter
