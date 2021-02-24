import 'dart:convert';

import 'package:meta/meta.dart';

extension DateTimeExtension on DateTime {
  static DateTime fromSecondsSinceEpoch(int seconds) {
    return DateTime.fromMillisecondsSinceEpoch(seconds * 1000);
  }
}

class Media {
  final String name;
  final Duration duration;
  final String path;
  final int size;
  final int width;
  final int height;
  final DateTime dateModified;
  Media({
    @required this.name,
    @required this.duration,
    @required this.path,
    @required this.size,
    @required this.width,
    @required this.height,
    @required this.dateModified,
  });

  factory Media.fromJson(Map<String, dynamic> json) {
    return Media(
      name: json['name'],
      duration: Duration(milliseconds: json['durationInMillis']),
      path: json['path'],
      size: json['sizeInBytes'],
      width: json['width'],
      height: json['height'],
      dateModified:
          DateTimeExtension.fromSecondsSinceEpoch(json['dateModified']),
    );
  }

  @override
  String toString() => jsonEncode({
        'name': name,
        'duration': duration.toString(),
        'path': path,
        'size': size,
        'width': width,
        'height': height,
        'dateModified': dateModified.toString(),
      });
}
