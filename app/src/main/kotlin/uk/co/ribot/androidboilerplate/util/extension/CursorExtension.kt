package uk.co.ribot.androidboilerplate.util.extension

import android.database.Cursor

fun Cursor.getString(columnName: String, defaultValue: String = ""): String {
    val index = getColumnIndex(columnName)
    return getString(index) ?: defaultValue
}

fun Cursor.getInt(columnName: String, defaultValue: Int = 0): Int {
    val index = getColumnIndex(columnName)
    return if (index >= 0) getInt(index) else defaultValue
}

fun Cursor.getLong(columnName: String, defaultValue: Long = 0): Long {
    val index = getColumnIndex(columnName)
    return if (index >= 0) getLong(index) else defaultValue
}

fun Cursor.getBoolean(columnName: String, defaultValue: Boolean = false): Boolean {
    val index = getColumnIndex(columnName)
    return if (index >= 0) getInt(index) == 1 else defaultValue
}
