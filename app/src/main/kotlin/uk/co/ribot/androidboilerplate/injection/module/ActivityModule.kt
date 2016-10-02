package uk.co.ribot.androidboilerplate.injection.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import uk.co.ribot.androidboilerplate.injection.ActivityContext
import javax.inject.Singleton

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @Singleton
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @Singleton
    @ActivityContext
    internal fun providesContext(): Context {
        return activity
    }
}
