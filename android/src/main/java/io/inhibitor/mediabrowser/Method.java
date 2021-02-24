package io.inhibitor.mediabrowser;

public enum Method {
    GetMediaList ("getMediaList");

    private final String name;

    Method(String name) {
        this.name = name;
    }

    public static Method fromName(String name) {
        for (Method method: Method.values()) {
            if (method.name.equals(name)) {
                return method;
            }
        }

        throw new IllegalArgumentException("unknown method: " + name);
    }
}
