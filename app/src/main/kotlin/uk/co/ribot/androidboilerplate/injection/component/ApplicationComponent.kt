package uk.co.ribot.androidboilerplate.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import uk.co.ribot.androidboilerplate.injection.ApplicationContext
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule

import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    @ApplicationContext fun context(): Context
    fun application(): Application
}
