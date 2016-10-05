package uk.co.ribot.androidboilerplate.util

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaPlugins
import rx.plugins.RxJavaSchedulersHook
import rx.schedulers.Schedulers

/**
 * This rule registers SchedulerHooks for RxJava and RxAndroid to ensure that subscriptions
 * always subscribeOn and observeOn Schedulers.immediate().
 * Warning, this rule will reset RxAndroidPlugins and RxJavaPlugins before and after each test so
 * if the application code uses RxJava plugins this may affect the behaviour of the testing method.
 */
class RxSchedulersOverrideRule : TestRule {

    private val mRxJavaSchedulersHook = object : RxJavaSchedulersHook() {
        override fun getIOScheduler(): Scheduler {
            return Schedulers.immediate()
        }

        override fun getNewThreadScheduler(): Scheduler {
            return Schedulers.immediate()
        }
    }

    private val mRxAndroidSchedulersHook = object : RxAndroidSchedulersHook() {
        override fun getMainThreadScheduler(): Scheduler {
            return Schedulers.immediate()
        }
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            override fun evaluate() {
                RxAndroidPlugins.getInstance().reset()
                RxAndroidPlugins.getInstance().registerSchedulersHook(mRxAndroidSchedulersHook)

                RxJavaPlugins.getInstance().reset()
                RxJavaPlugins.getInstance().registerSchedulersHook(mRxJavaSchedulersHook)

                base.evaluate()

                RxAndroidPlugins.getInstance().reset()
                RxJavaPlugins.getInstance().reset()
            }
        }
    }
}
