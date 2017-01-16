package uk.co.ribot.androidboilerplate.injection.component

import javax.inject.Singleton

import dagger.Component
import uk.co.ribot.androidboilerplate.injection.module.ApplicationTestModule

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : ApplicationComponent
