package uk.co.ribot.androidboilerplate.injection.module

import android.app.Application
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import dagger.Provides
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper
import uk.co.ribot.androidboilerplate.data.remote.RibotsService
import uk.co.ribot.androidboilerplate.injection.ApplicationContext
import javax.inject.Singleton

class ApplicationTestModule(val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    /***** MOCKS *****/

    fun ribotsService(): RibotsService {
        return mock()
    }

    fun databaseHelper(): DatabaseHelper {
        return mock()
    }

    fun dataManager(): DataManager {
        return mock()
    }
}
