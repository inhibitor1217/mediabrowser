import 'package:flutter/material.dart';
import 'dart:async';

import 'package:mediabrowser/mediabrowser.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void didChangeDependencies() {
    _loadMedia();

    super.didChangeDependencies();
  }

  Future<void> _loadMedia() async {
    final result = await MediaBrowser.getMediaList();
    print(result);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(),
      ),
    );
  }
}
