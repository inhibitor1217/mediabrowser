package io.inhibitor.mediabrowser;

public enum MediaBrowserErrorCode {
    PermissionRejected("PERMISSION_REJECTED"),
    ThumbnailExtractionFailed("THUMBNAIL_EXTRACTION_FAILED"),
    ThumbnailEmptyData("THUMBNAIL_EMPTY_DATA");

    private final String name;

    MediaBrowserErrorCode(String name) {
        this.name = name;
    }
}
