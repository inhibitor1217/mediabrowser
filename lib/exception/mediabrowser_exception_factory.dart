import 'package:flutter/services.dart';
import 'package:mediabrowser/exception/mediabrowser_unknown_exception.dart';
import 'package:mediabrowser/exception/permission_rejected_exception.dart';

class MediaBrowserExceptionFactory {
  static Error create(PlatformException e) {
    switch (e.code) {
      case 'PermissionRejected':
        return PermissionRejectedException(
          message: e.message,
          details: e.details,
        );
      default:
        return MediaBrowserUnknownException(unknownErrorCode: e.code);
    }
  }
}
