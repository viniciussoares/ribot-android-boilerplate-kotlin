package uk.co.ribot.androidboilerplate.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import uk.co.ribot.androidboilerplate.injection.ApplicationContext

/**
 * Provide application-level dependencies.
 */
@Module
class ApplicationModule(val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }
}
