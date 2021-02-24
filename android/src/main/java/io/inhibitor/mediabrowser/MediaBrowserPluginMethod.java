package io.inhibitor.mediabrowser;

public enum MediaBrowserPluginMethod {
    GetMediaList ("getMediaList");

    private final String name;

    MediaBrowserPluginMethod(String name) {
        this.name = name;
    }

    public static MediaBrowserPluginMethod fromName(String name) {
        for (MediaBrowserPluginMethod method: MediaBrowserPluginMethod.values()) {
            if (method.name.equals(name)) {
                return method;
            }
        }

        throw new IllegalArgumentException("unknown method: " + name);
    }
}
