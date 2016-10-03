package uk.co.ribot.androidboilerplate.injection.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(ApiModule::class, DbModule::class))
class DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("ribots", MODE_PRIVATE)
    }
}
