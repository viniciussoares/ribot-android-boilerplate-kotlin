package uk.co.ribot.androidboilerplate.util.extension

import android.database.Cursor

fun Cursor.getString(columnName: String): String {
    return getString(getColumnIndexOrThrow(columnName))
}

fun Cursor.getInt(columnName: String): Int {
    return getInt(getColumnIndexOrThrow(columnName))
}

fun Cursor.getLong(columnName: String): Long {
    return getLong(getColumnIndexOrThrow(columnName))
}

fun Cursor.getBoolean(columnName: String): Boolean {
    return getInt(columnName) == 1
}
