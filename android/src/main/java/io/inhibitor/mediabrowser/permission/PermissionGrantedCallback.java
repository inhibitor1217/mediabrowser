package io.inhibitor.mediabrowser.permission;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.inhibitor.mediabrowser.MediaBrowserDelegate;
import io.inhibitor.mediabrowser.MediaBrowserErrorCode;

public class PermissionGrantedCallback {
    private final MethodCall call;
    private final MethodChannel.Result result;
    private final MediaBrowserDelegate.MediaBrowsingAction callbackAction;

    public PermissionGrantedCallback(MethodCall call,
                              MethodChannel.Result result,
                              MediaBrowserDelegate.MediaBrowsingAction callbackAction) {
        this.call = call;
        this.result = result;
        this.callbackAction = callbackAction;
    }

    public void call(MediaBrowserDelegate delegate) {
        delegate.executeAction(callbackAction, call, result);
    }

    public void fail(String rejectedPermission) {
        result.error(MediaBrowserErrorCode.PermissionRejected.name(),
                "Requested permission was rejected by user.",
                rejectedPermission);
    }
}
