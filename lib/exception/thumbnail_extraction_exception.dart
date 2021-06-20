import 'package:mediabrowser/exception/base_exception.dart';
import 'package:meta/meta.dart';

class ThumbnailExtractionException extends BaseException {
  ThumbnailExtractionException({
    @required String message,
    @required String details,
  }) : super(message: message, details: details);
}
