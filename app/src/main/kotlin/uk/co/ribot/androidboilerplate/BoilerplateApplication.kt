package uk.co.ribot.androidboilerplate

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import uk.co.ribot.androidboilerplate.injection.component.ApplicationComponent
import uk.co.ribot.androidboilerplate.injection.component.DaggerApplicationComponent
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule

class BoilerplateApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this))
            return

        LeakCanary.install(this)

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
