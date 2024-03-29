package io.inhibitor.mediabrowser.adapter;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import io.inhibitor.mediabrowser.dto.Media;

public class MediaCursorAdapter {
    private final Cursor cursor;
    private final CursorAdapter adapter;

    MediaCursorAdapter(Cursor cursor, CursorAdapter adapter) {
        this.cursor = cursor;
        this.adapter = adapter;
    }

    public MediaCursorAdapter(Cursor cursor) {
        this(cursor, new CursorAdapter(cursor));
    }

    public List<Media> getAllMedias() {
        List<Media> medias = new ArrayList<>();

        cursor.moveToFirst();
        do {
            medias.add(getMediaFromCurrent());
        } while (cursor.moveToNext());

        return medias;
    }

    public Media getMediaFromCurrent() {
        if (cursor.isAfterLast()) {
            return null;
        }

        long id = cursor.getLong(adapter.getColumnIndex(MediaStore.Video.Media._ID));
        String displayName = cursor.getString(adapter.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
        int duration = cursor.getInt(adapter.getColumnIndex(MediaStore.Video.Media.DURATION));
        String path = cursor.getString(adapter.getColumnIndex(MediaStore.Video.Media.DATA));
        int size = cursor.getInt(adapter.getColumnIndex(MediaStore.Video.Media.SIZE));
        int width = cursor.getInt(adapter.getColumnIndex(MediaStore.Video.Media.WIDTH));
        int height = cursor.getInt(adapter.getColumnIndex(MediaStore.Video.Media.HEIGHT));
        int dateModified = cursor.getInt(adapter.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));

        Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

        return new Media(contentUri, displayName, duration, path, size, width, height, dateModified);
    }
}
