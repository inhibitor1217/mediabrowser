import 'package:mediabrowser/exception/base_exception.dart';
import 'package:meta/meta.dart';

class PermissionRejectedException extends BaseException {
  PermissionRejectedException({
    @required String message,
    @required String details,
  }) : super(message: message, details: details);
}
