import 'package:flutter/services.dart';
import 'package:mediabrowser/exception/mediabrowser_unknown_exception.dart';
import 'package:mediabrowser/exception/permission_rejected_exception.dart';
import 'package:mediabrowser/exception/thumbnail_empty_exception.dart';

import '../mediabrowser.dart';

class MediaBrowserExceptionFactory {
  static Error create(PlatformException e) {
    switch (e.code) {
      case 'PermissionRejected':
        return PermissionRejectedException(
          message: e.message,
          details: e.details,
        );
      case 'ThumbnailExtractionFailed':
        return ThumbnailExtractionException(
          message: e.message,
          details: e.details,
        );
      case 'ThumbnailEmptyData':
        return ThumbnailEmptyException(
          message: e.message,
          details: e.details,
        );
      default:
        return MediaBrowserUnknownException(unknownErrorCode: e.code);
    }
  }
}
