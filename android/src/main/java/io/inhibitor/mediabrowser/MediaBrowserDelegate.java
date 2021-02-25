package io.inhibitor.mediabrowser;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;

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
import io.inhibitor.mediabrowser.util.ExternalStorageQueryHandler;
import io.inhibitor.mediabrowser.util.Logger;
import io.inhibitor.mediabrowser.util.ThumbnailExtractor;

public class MediaBrowserDelegate implements PluginRegistry.RequestPermissionsResultListener {
    public enum MediaBrowsingAction {
        ListMedia,
    }

    private final Activity activity;
    private final Logger logger;
    private final ExternalStorageQueryHandler externalStorageQueryHandler;
    private final ThumbnailExtractor thumbnailExtractor;

    private final MediaPermissionManager permissionManager;

    MediaBrowserDelegate(Activity activity,
                         Logger logger,
                         ExternalStorageQueryHandler externalStorageQueryHandler,
                         ThumbnailExtractor thumbnailExtractor) {
        this.activity = activity;
        this.logger = logger;
        this.externalStorageQueryHandler = externalStorageQueryHandler;
        this.thumbnailExtractor = thumbnailExtractor;

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

        Cursor cursor = externalStorageQueryHandler.queryAll();
        MediaCursorAdapter mediaCursorAdapter = new MediaCursorAdapter(cursor);
        List<Media> medias = mediaCursorAdapter.getAllMedias();

        List<String> serializedMedias = new ArrayList<>();
        for (Media media : medias) {
            serializedMedias.add(media.toString());
        }

        result.success(serializedMedias);
    }

    public void requestThumbnail(MethodCall call, MethodChannel.Result result) {
        String path = call.argument("path");

        thumbnailExtractor.extractThumbnailByteArray(path, result);
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode,
                                              String[] permissions,
                                              int[] grantResults) {
        return permissionManager.onRequestPermissionsResult(permissions, grantResults, requestCode);
    }
}
