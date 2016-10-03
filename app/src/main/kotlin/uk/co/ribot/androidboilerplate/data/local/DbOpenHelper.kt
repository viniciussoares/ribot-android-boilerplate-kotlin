package uk.co.ribot.androidboilerplate.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import okio.BufferedSource
import okio.Okio
import timber.log.Timber

import java.io.IOException
import java.nio.charset.Charset
import java.sql.SQLException

class DbOpenHelper constructor(val context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "ribots.db"
        val DATABASE_VERSION = 1
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        //Uncomment line below if you want to enable foreign keys
        //db.execSQL("PRAGMA foreign_keys=ON;");
    }

    override fun onCreate(db: SQLiteDatabase) {
        assert(DATABASE_VERSION > 0)
        Timber.i("Creating database")
        migrate(db, 1, DATABASE_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Timber.i("Upgrading database")
        migrate(db, oldVersion.inc(), newVersion)
    }

    fun migrate(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.beginTransaction()

        try {
            for (i in oldVersion..newVersion)
                runMigration(db, i)
            db.setTransactionSuccessful()
        } catch (exception: SQLException) {
            Timber.e(exception)
        }

        db.endTransaction()
    }

    fun runMigration(db: SQLiteDatabase, migration: Int) {
        Timber.i("Running Migration $migration")

        val fileName = "$migration.sql"

        var bufferedSource: BufferedSource? = null

        try {
            val inputStream = context.assets.open("sql/$fileName")
            bufferedSource = Okio.buffer(Okio.source(inputStream))
            val migrationString = bufferedSource.readString(Charset.defaultCharset())

            migrationString.split("\n")
                    .filter { !it.startsWith("-") }
                    .filter(String::isNotBlank)
                    .reduce { a, b -> "$a$b" }
                    .split(";")
                    .filter(String::isNotBlank)
                    .forEach {
                        Timber.v("Running SQL: $it")
                        db.execSQL(it)
                    }

        } catch (e: IOException) {
            Timber.wtf(e, "$fileName not found.")
        } catch (e: SQLException) {
            Timber.e(e, "Error executing SQL statement: $fileName")
        }

        try {
            bufferedSource?.close()
        } catch (e: IOException) {
            Timber.wtf(e)
        }
    }
}
