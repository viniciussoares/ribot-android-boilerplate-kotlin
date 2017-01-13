package uk.co.ribot.androidboilerplate

import android.app.Application
import android.support.annotation.VisibleForTesting
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import uk.co.ribot.androidboilerplate.injection.component.ApplicationComponent
import uk.co.ribot.androidboilerplate.injection.component.DaggerApplicationComponent
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule

class BoilerplateApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this))
            return
        LeakCanary.install(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initDaggerComponent()
    }

    @VisibleForTesting
    fun initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
