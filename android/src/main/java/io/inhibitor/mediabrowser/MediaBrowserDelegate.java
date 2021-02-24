package io.inhibitor.mediabrowser;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.inhibitor.mediabrowser.adapter.MediaCursorAdapter;
import io.inhibitor.mediabrowser.dto.Media;

public class MediaBrowserDelegate {
    private static final String[] mediaQueryProjection = new String[] {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_MODIFIED,
    };

    private static final String mediaQuerySortOrder = MediaStore.Video.Media.DATE_MODIFIED + " DESC";

    private final Activity activity;
    private final Logger logger;

    MediaBrowserDelegate(Activity activity,
                         Logger logger) {
        this.activity = activity;
        this.logger = logger;
    }

    public void listMedias(MethodCall call, MethodChannel.Result result) {
        Cursor cursor = queryExternalStorage();
        MediaCursorAdapter mediaCursorAdapter = new MediaCursorAdapter(cursor);
        List<Media> medias = mediaCursorAdapter.getAllMedias();

        List<String> serializedMedias = new ArrayList<>();
        for (Media media : medias) {
            serializedMedias.add(media.toString());
        }

        result.success(serializedMedias);
    }

    private Cursor queryExternalStorage() {
        return activity
                .getApplicationContext()
                .getContentResolver()
                .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        mediaQueryProjection,
                        null,
                        null,
                        mediaQuerySortOrder);
    }
}
