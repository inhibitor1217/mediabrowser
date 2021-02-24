package io.inhibitor.mediabrowser;

public enum MediaBrowserErrorCode {
    PermissionRejected("PERMISSION_REJECTED");

    private final String name;

    MediaBrowserErrorCode(String name) {
        this.name = name;
    }
}
