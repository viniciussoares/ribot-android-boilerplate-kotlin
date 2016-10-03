package uk.co.ribot.androidboilerplate.injection.module

import android.app.Application
import com.squareup.sqlbrite.BriteDatabase
import com.squareup.sqlbrite.SqlBrite
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import timber.log.Timber
import uk.co.ribot.androidboilerplate.data.local.DbOpenHelper

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideOpenHelper(application: Application): DbOpenHelper {
        return DbOpenHelper(application)
    }

    @Provides
    @Singleton
    fun provideSqlBrite(): SqlBrite {
        return SqlBrite.create(SqlBrite.Logger { message -> Timber.tag("Database").v(message) })
    }

    @Provides
    @Singleton
    fun provideDatabase(sqlBrite: SqlBrite, helper: DbOpenHelper): BriteDatabase {
        val db = sqlBrite.wrapDatabaseHelper(helper)
        db.setLoggingEnabled(true)
        return db
    }
}
