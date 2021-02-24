package io.inhibitor.mediabrowser.util;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

public class ExternalStorageQueryHandler {
    private static final String[] mediaQueryProjection = new String[] {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.WIDTH,
            MediaStore.Video.Media.HEIGHT,
            MediaStore.Video.Media.DATE_MODIFIED,
    };

    private static final String mediaQuerySortOrder = MediaStore.Video.Media.DATE_MODIFIED + " DESC";

    private final Activity activity;

    public ExternalStorageQueryHandler(Activity activity) {
        this.activity = activity;
    }

    public Cursor queryAll() {
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
