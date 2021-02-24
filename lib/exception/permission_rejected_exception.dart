import 'package:meta/meta.dart';

class PermissionRejectedException extends Error {
  final String message;
  final String details;
  PermissionRejectedException({
    @required this.message,
    @required this.details,
  });

  @override
  String toString() => '$message: $details';
}
