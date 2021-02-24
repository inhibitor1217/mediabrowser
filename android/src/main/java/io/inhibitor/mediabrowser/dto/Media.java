package io.inhibitor.mediabrowser.dto;

import android.net.Uri;

import java.util.Locale;

public class Media {
    public final Uri uri;
    public final String name;
    public final int durationInMillis;
    public final String path;
    public final int sizeInBytes;
    public final int width;
    public final int height;
    public final int dateModified;

    public Media(Uri uri,
                 String name,
                 int durationInMillis,
                 String path,
                 int sizeInBytes,
                 int width,
                 int height,
                 int dateModified) {
        this.uri = uri;
        this.name = name;
        this.durationInMillis = durationInMillis;
        this.path = path;
        this.sizeInBytes = sizeInBytes;
        this.width = width;
        this.height = height;
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ENGLISH,
                "{\"path\":\"%s\",\"name\":\"%s\",\"durationInMillis\":%d,\"path\":\"%s\",\"sizeInBytes\":%d,\"width\":%d,\"height\":%d,\"dateModified\":%d}",
                uri.getPath(), name, durationInMillis, path, sizeInBytes, width, height, dateModified);
    }
}
