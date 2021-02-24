package io.inhibitor.mediabrowser.adapter;

import android.database.Cursor;

import java.util.HashMap;

public class CursorAdapter {
    private final Cursor cursor;
    private final HashMap<String, Integer> columnIndicesCache;

    CursorAdapter(Cursor cursor) {
        this.cursor = cursor;

        this.columnIndicesCache = new HashMap<>();
    }

    public int getColumnIndex(String column) {
        if (columnIndicesCache.containsKey(column)) {
            return columnIndicesCache.get(column);
        }

        int columnIndex = cursor.getColumnIndexOrThrow(column);
        columnIndicesCache.put(column, columnIndex);
        return columnIndex;
    }
}
