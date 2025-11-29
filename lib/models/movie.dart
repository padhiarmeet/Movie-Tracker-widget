class Movie {
  final int? id;
  final String name;
  final DateTime releaseDate;
  final String? imagePath;
  final String? cardColor; // Hex color string

  const Movie({
    this.id,
    required this.name,
    required this.releaseDate,
    this.imagePath,
    this.cardColor,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'releaseDate': releaseDate.toIso8601String(),
      'imagePath': imagePath,
      'cardColor': cardColor,
    };
  }

  factory Movie.fromMap(Map<String, dynamic> map) {
    return Movie(
      id: map['id'] as int?,
      name: map['name'] as String,
      releaseDate: DateTime.parse(map['releaseDate'] as String),
      imagePath: map['imagePath'] as String?,
      cardColor: map['cardColor'] as String?,
    );
  }
}
