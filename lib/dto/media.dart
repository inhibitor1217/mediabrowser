import 'dart:convert';

import 'package:meta/meta.dart';

extension DateTimeExtension on DateTime {
  static DateTime fromSecondsSinceEpoch(int seconds) {
    return DateTime.fromMillisecondsSinceEpoch(seconds * 1000);
  }
}

class Media {
  final Uri uri;
  final String name;
  final Duration duration;
  final int size;
  final DateTime dateModified;
  Media({
    @required this.uri,
    @required this.name,
    @required this.duration,
    @required this.size,
    @required this.dateModified,
  });

  factory Media.fromJson(Map<String, dynamic> json) {
    return Media(
      uri: Uri.parse(json['path']),
      name: json['name'],
      duration: Duration(milliseconds: json['durationInMillis']),
      size: json['sizeInBytes'],
      dateModified:
          DateTimeExtension.fromSecondsSinceEpoch(json['dateModified']),
    );
  }

  @override
  String toString() => jsonEncode({
        'path': uri.path,
        'name': name,
        'duration': duration.toString(),
        'size': size,
        'dateModified': dateModified.toString(),
      });
}
