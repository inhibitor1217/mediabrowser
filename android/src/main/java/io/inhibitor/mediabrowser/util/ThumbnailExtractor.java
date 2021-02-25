package io.inhibitor.mediabrowser.util;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

import io.flutter.plugin.common.MethodChannel;

public class ThumbnailExtractor {
    private final ThumbnailUtils thumbnailUtils;

    public ThumbnailExtractor() {
        this.thumbnailUtils = new ThumbnailUtils();
    }

    private class ThumbnailExtractTask extends AsyncTask<String, Void, byte[]> {
        private final MethodChannel.Result result;

        ThumbnailExtractTask(MethodChannel.Result result) {
            this.result = result;
        }

        @Override
        protected byte[] doInBackground(String... paths) {
            String path = paths[0];

            Bitmap thumbnail = thumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            byte[] thumbnailByteArray = outStream.toByteArray();
            thumbnail.recycle();
            return thumbnailByteArray;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            result.success(bytes);
        }
    }

    public void extractThumbnailByteArray(String path, MethodChannel.Result result) {
        new ThumbnailExtractTask(result).execute(path);
    }
}
