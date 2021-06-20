import 'package:flutter/services.dart';
import 'package:mediabrowser/exception/mediabrowser_unknown_exception.dart';
import 'package:mediabrowser/exception/permission_rejected_exception.dart';
import 'package:mediabrowser/exception/thumbnail_empty_exception.dart';

import '../mediabrowser.dart';

class MediaBrowserExceptionFactory {
  static Error create(PlatformException e) {
    switch (e.code) {
      case 'PERMISSION_REJECTED':
        return PermissionRejectedException(
          message: e.message,
          details: e.details,
        );
      case 'THUMBNAIL_EXTRACTION_FAILED':
        return ThumbnailExtractionException(
          message: e.message,
          details: e.details,
        );
      case 'THUMBNAIL_EMPTY_DATA':
        return ThumbnailEmptyException(
          message: e.message,
          details: e.details,
        );
      default:
        return MediaBrowserUnknownException(unknownErrorCode: e.code);
    }
  }
}
