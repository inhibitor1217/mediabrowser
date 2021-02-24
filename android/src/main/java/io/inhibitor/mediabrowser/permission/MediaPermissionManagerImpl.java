package io.inhibitor.mediabrowser.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;

import io.inhibitor.mediabrowser.MediaBrowserDelegate;

public class MediaPermissionManagerImpl implements MediaPermissionManager {
    private MediaBrowserDelegate delegate;
    private Activity activity;
    private Context context;

    private HashMap<Integer, PermissionGrantedCallback> callbacks;
    private int callbackIdCount;

    MediaPermissionManagerImpl(MediaBrowserDelegate delegate,
                               Activity activity,
                               Context context) {
        this.delegate = delegate;
        this.activity = activity;
        this.context = context;

        this.callbacks = new HashMap<>();
        this.callbackIdCount = 0;
    }

    @Override
    public boolean isPermissionGranted(String requestedPermission) {
        return ContextCompat.checkSelfPermission(context, requestedPermission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermission(String requestedPermission,
                                 PermissionGrantedCallback callback) {
        int callbackId = registerCallback(callback);
        ActivityCompat.requestPermissions(activity, new String[] { requestedPermission }, callbackId);
    }

    private int registerCallback(PermissionGrantedCallback callback) {
        int id = generateCallbackId();
        callbacks.put(id, callback);
        return id;
    }

    private int generateCallbackId() {
        return callbackIdCount++;
    }

    @Override
    public void onRequestPermissionsResult(String[] requestedPermissions,
                                           int[] grantResults,
                                           int requestCode) {
        PermissionGrantedCallback callback = getCallback(requestCode);

        for (int i = 0; i < grantResults.length; i++) {
            int grantResult = grantResults[i];
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                String failedPermission = requestedPermissions[i];
                callback.fail(failedPermission);
                return;
            }
        }

        callback.call(delegate);
    }

    private PermissionGrantedCallback getCallback(int id) {
        return callbacks.get(id);
    }
}
