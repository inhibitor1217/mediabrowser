package io.inhibitor.mediabrowser.util;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

import io.flutter.plugin.common.MethodChannel;
import io.inhibitor.mediabrowser.MediaBrowserErrorCode;

public class ThumbnailExtractor {
    private final ThumbnailUtils thumbnailUtils;

    public ThumbnailExtractor() {
        this.thumbnailUtils = new ThumbnailUtils();
    }

    private class ThumbnailExtractTask extends AsyncTask<String, Void, byte[]> {
        private final MethodChannel.Result result;

        private Exception exception = null;

        ThumbnailExtractTask(MethodChannel.Result result) {
            this.result = result;
        }

        @Override
        protected byte[] doInBackground(String... paths) {
            String path = paths[0];

            try {
                Bitmap thumbnail = thumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                byte[] thumbnailByteArray = outStream.toByteArray();
                thumbnail.recycle();
                return thumbnailByteArray;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            if (this.exception != null) {
                result.error(MediaBrowserErrorCode.ThumbnailExtractionFailed.name(),
                    this.exception.getMessage(),
                    null);
                return;
            }

            if (bytes == null) {
                result.error(MediaBrowserErrorCode.ThumbnailEmptyData.name(),
                    "No data found",
                    null);
                return;
            }

            result.success(bytes);
        }
    }

    public void extractThumbnailByteArray(String path, MethodChannel.Result result) {
        new ThumbnailExtractTask(result).execute(path);
    }
}
