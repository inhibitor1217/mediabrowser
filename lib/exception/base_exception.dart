import 'package:meta/meta.dart';

class BaseException extends Error {
  final String message;
  final String details;
  BaseException({
    @required this.message,
    @required this.details,
  });

  @override
  String toString() => '$message: $details';
}
