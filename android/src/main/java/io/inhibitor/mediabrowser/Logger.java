package io.inhibitor.mediabrowser;

import android.util.Log;

import java.util.Locale;

public class Logger {
    private String tag;

    Logger(String tag) {
        this.tag = tag;
    }

    public void log(String message) {
        Log.i(tag, message);
    }

    public void log(String formatString, Object... args) {
        String message = String.format(Locale.ENGLISH, formatString, args);
        log(message);
    }
}
