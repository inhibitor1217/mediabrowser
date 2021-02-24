package io.inhibitor.mediabrowser.util;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

public class ThumbnailExtractor {
    private final ThumbnailUtils thumbnailUtils;

    public ThumbnailExtractor() {
        this.thumbnailUtils = new ThumbnailUtils();
    }

    public byte[] extractThumbnailByteArray(String path) {
        Bitmap thumbnail = thumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        byte[] thumbnailByteArray = outStream.toByteArray();
        thumbnail.recycle();
        return thumbnailByteArray;
    }
}
