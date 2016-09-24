package uk.co.ribot.androidboilerplate

import android.app.Application
import timber.log.Timber
import uk.co.ribot.androidboilerplate.injection.component.ApplicationComponent
import uk.co.ribot.androidboilerplate.injection.component.DaggerApplicationComponent
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule

class BoilerplateApplication: Application() {

    private var _applicationComponent: ApplicationComponent? = null
    val applicationComponent: ApplicationComponent
        get() {
            if (_applicationComponent == null)
                DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this))
                        .build()
            return _applicationComponent ?: throw AssertionError()
        }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
