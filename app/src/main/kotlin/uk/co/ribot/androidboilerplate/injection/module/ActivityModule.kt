package uk.co.ribot.androidboilerplate.injection.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import uk.co.ribot.androidboilerplate.injection.ActivityContext
import uk.co.ribot.androidboilerplate.injection.PerActivity

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @PerActivity
    @ActivityContext
    internal fun providesContext(): Context {
        return activity
    }
}
