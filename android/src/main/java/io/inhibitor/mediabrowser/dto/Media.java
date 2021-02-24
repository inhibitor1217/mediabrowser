package io.inhibitor.mediabrowser.dto;

import android.net.Uri;

import java.util.Locale;

public class Media {
    public final Uri uri;
    public final String name;
    public final int durationInMillis;
    public final int sizeInBytes;
    public final int dateModified;

    public Media(Uri uri, String name, int durationInMillis, int sizeInBytes, int dateModified) {
        this.uri = uri;
        this.name = name;
        this.durationInMillis = durationInMillis;
        this.sizeInBytes = sizeInBytes;
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ENGLISH,
                "{\"path\":\"%s\",\"name\":\"%s\",\"durationInMillis\":%d,\"sizeInBytes\":%d,\"dateModified\":%d}",
                uri.getPath(), name, durationInMillis, sizeInBytes, dateModified);
    }
}
