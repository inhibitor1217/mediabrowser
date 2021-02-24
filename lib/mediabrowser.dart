import 'dart:async';
import 'dart:convert';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import './dto/media.dart';
import './exception/mediabrowser_exception_factory.dart';

export './dto/media.dart' show Media;

class MediaBrowser {
  static const MethodChannel _channel = const MethodChannel('mediabrowser');

  static Future<List<Media>> getMediaList() async {
    try {
      final List<dynamic> mediaJsonList =
          await _channel.invokeMethod('getMediaList');
      final medias =
          mediaJsonList.map((raw) => Media.fromJson(jsonDecode(raw))).toList();
      return medias;
    } catch (e) {
      if (e is PlatformException) {
        throw MediaBrowserExceptionFactory.create(e);
      }
      throw e;
    }
  }

  static Future<Image> getMediaThumbnail(String path) async {
    try {
      final Uint8List thumbnailByteArray = await _channel.invokeMethod(
        'requestThumbnail',
        {'path': path},
      );

      return Image.memory(thumbnailByteArray);
    } catch (e) {
      if (e is PlatformException) {
        throw MediaBrowserExceptionFactory.create(e);
      }
      throw e;
    }
  }
}
