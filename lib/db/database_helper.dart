import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import '../models/movie.dart';

class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._init();
  static Database? _database;

  DatabaseHelper._init();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDB('movies.db');
    return _database!;
  }

  Future<Database> _initDB(String filePath) async {
    final dbPath = await getDatabasesPath();
    final path = join(dbPath, filePath);

    return await openDatabase(
      path, 
      version: 2, 
      onCreate: _createDB,
      onUpgrade: _upgradeDB,
    );
  }

  Future _createDB(Database db, int version) async {
    const idType = 'INTEGER PRIMARY KEY AUTOINCREMENT';
    const textType = 'TEXT NOT NULL';
    const textNullable = 'TEXT';

    await db.execute('''
CREATE TABLE movies ( 
  id $idType, 
  name $textType,
  releaseDate $textType,
  imagePath $textNullable,
  cardColor $textNullable
  )
''');
  }
  
  Future _upgradeDB(Database db, int oldVersion, int newVersion) async {
    if (oldVersion < 2) {
      await db.execute('ALTER TABLE movies ADD COLUMN cardColor TEXT');
    }
  }

  Future<int> create(Movie movie) async {
    final db = await instance.database;
    return await db.insert('movies', movie.toMap());
  }

  Future<List<Movie>> readAllMovies({String sortBy = 'releaseDate'}) async {
    final db = await instance.database;
    String orderBy;
    
    switch (sortBy) {
      case 'name':
        orderBy = 'name COLLATE NOCASE ASC';
        break;
      case 'rating':
        orderBy = 'rating DESC, releaseDate ASC';
        break;
      case 'releaseDate':
      default:
        orderBy = 'releaseDate ASC';
    }
    
    final result = await db.query('movies', orderBy: orderBy);

    return result.map((json) => Movie.fromMap(json)).toList();
  }

  Future<int> delete(int id) async {
    final db = await instance.database;
    return await db.delete(
      'movies',
      where: 'id = ?',
      whereArgs: [id],
    );
  }

  Future close() async {
    final db = await instance.database;
    db.close();
  }
}
