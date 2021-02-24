package io.inhibitor.mediabrowser.permission;

import android.app.Activity;

import io.inhibitor.mediabrowser.MediaBrowserDelegate;

public class MediaPermissionManagerFactory {
    public static MediaPermissionManager create(MediaBrowserDelegate delegate,
                                                Activity activity) {
        return new MediaPermissionManagerImpl(delegate,
                activity,
                activity.getApplicationContext());
    }
}
