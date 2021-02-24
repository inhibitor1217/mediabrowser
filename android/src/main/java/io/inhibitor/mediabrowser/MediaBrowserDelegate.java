package io.inhibitor.mediabrowser;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.inhibitor.mediabrowser.adapter.MediaCursorAdapter;
import io.inhibitor.mediabrowser.dto.Media;
import io.inhibitor.mediabrowser.permission.MediaPermissionManager;
import io.inhibitor.mediabrowser.permission.MediaPermissionManagerFactory;
import io.inhibitor.mediabrowser.permission.PermissionGrantedCallback;
import io.inhibitor.mediabrowser.util.Logger;

public class MediaBrowserDelegate implements PluginRegistry.RequestPermissionsResultListener {
    private static final String[] mediaQueryProjection = new String[] {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_MODIFIED,
    };

    private static final String mediaQuerySortOrder = MediaStore.Video.Media.DATE_MODIFIED + " DESC";

    public enum MediaBrowsingAction {
        ListMedia,
    }

    private final Activity activity;
    private final Logger logger;

    private final MediaPermissionManager permissionManager;

    MediaBrowserDelegate(Activity activity,
                         Logger logger) {
        this.activity = activity;
        this.logger = logger;

        this.permissionManager = MediaPermissionManagerFactory.create(this, activity);
    }

    public void executeAction(MediaBrowsingAction action,
                              MethodCall call,
                              MethodChannel.Result result) {
        switch (action) {
            case ListMedia:
                listMedias(call, result);
                break;
        }
    }

    public void listMedias(MethodCall call, MethodChannel.Result result) {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (!permissionManager.isPermissionGranted(permission)) {
            permissionManager.requestPermission(permission,
                    new PermissionGrantedCallback(call, result, MediaBrowsingAction.ListMedia));
            return;
        }

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

    @Override
    public boolean onRequestPermissionsResult(int requestCode,
                                              String[] permissions,
                                              int[] grantResults) {
        return permissionManager.onRequestPermissionsResult(permissions, grantResults, requestCode);
    }
}
