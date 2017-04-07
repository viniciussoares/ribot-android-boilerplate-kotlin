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
import rx.functions.Func1
import rx.plugins.RxJavaHooks


/**
 * This rule registers SchedulerHooks for RxJava and RxAndroid to ensure that subscriptions
 * always subscribeOn and observeOn Schedulers.immediate().
 * Warning, this rule will reset RxAndroidPlugins and RxJavaPlugins before and after each test so
 * if the application code uses RxJava plugins this may affect the behaviour of the testing method.
 */
class RxSchedulersOverrideRule : TestRule {

    private val mRxAndroidSchedulersHook = object : RxAndroidSchedulersHook() {
        override fun getMainThreadScheduler(): Scheduler {
            return Schedulers.immediate()
        }
    }

    private val mRxJavaImmediateScheduler = Func1<Scheduler, Scheduler> { Schedulers.immediate() }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            override fun evaluate() {
                RxAndroidPlugins.getInstance().reset()
                RxAndroidPlugins.getInstance().registerSchedulersHook(mRxAndroidSchedulersHook)

                RxJavaHooks.reset()
                RxJavaHooks.setOnIOScheduler(mRxJavaImmediateScheduler)
                RxJavaHooks.setOnNewThreadScheduler(mRxJavaImmediateScheduler)

                base.evaluate()

                RxAndroidPlugins.getInstance().reset()
                RxJavaHooks.reset()
            }
        }
    }
}
