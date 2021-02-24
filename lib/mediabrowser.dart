import 'dart:async';
import 'dart:convert';

import 'package:flutter/services.dart';

import './dto/media.dart';

class MediaBrowser {
  static const MethodChannel _channel = const MethodChannel('mediabrowser');

  static Future<List<Media>> getMediaList() async {
    final List<dynamic> mediaJsonList =
        await _channel.invokeMethod('getMediaList');
    final medias =
        mediaJsonList.map((raw) => Media.fromJson(jsonDecode(raw))).toList();
    return medias;
  }
}
