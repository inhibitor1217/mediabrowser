import 'package:flutter/material.dart';
import 'package:mediabrowser/mediabrowser.dart';
import 'package:mediabrowser/dto/media.dart';

class _MediaThumbnail extends StatefulWidget {
  final path;
  _MediaThumbnail({
    Key key,
    @required this.path,
  }) : super(key: key);

  @override
  _MediaThumbnailState createState() => _MediaThumbnailState();
}

class _MediaThumbnailState extends State<_MediaThumbnail> {
  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Image>(
      future: MediaBrowser.getMediaThumbnail(widget.path),
      builder: (context, snapshot) =>
          snapshot.hasData ? snapshot.data : Text('Loading ...'),
    );
  }
}

class MediaThumbnails extends StatefulWidget {
  @override
  _MediaThumbnailsState createState() => _MediaThumbnailsState();
}

class _MediaThumbnailsState extends State<MediaThumbnails> {
  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<Media>>(
      future: MediaBrowser.getMediaList(),
      builder: (context, snapshot) => Column(
        children: <Widget>[
          if (snapshot.hasData)
            ...snapshot.data.map(
              (media) => _MediaThumbnail(
                key: ValueKey<String>(media.name),
                path: media.path,
              ),
            ),
        ],
      ),
    );
  }
}
