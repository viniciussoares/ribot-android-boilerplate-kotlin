package uk.co.ribot.androidboilerplate.injection.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import uk.co.ribot.androidboilerplate.injection.ActivityContext

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return activity
    }
}
