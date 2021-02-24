import 'package:meta/meta.dart';

class MediaBrowserUnknownException extends Error {
  final String unknownErrorCode;
  MediaBrowserUnknownException({
    @required this.unknownErrorCode,
  });

  @override
  String toString() => 'unknown error code: $unknownErrorCode';
}
