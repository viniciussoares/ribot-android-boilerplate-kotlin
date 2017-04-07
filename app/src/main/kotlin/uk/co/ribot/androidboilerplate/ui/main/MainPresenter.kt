package uk.co.ribot.androidboilerplate.ui.main

import rx.Subscription
import javax.inject.Inject

import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscribeBy
import rx.schedulers.Schedulers
import timber.log.Timber
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.injection.ConfigPersistent

@ConfigPersistent
class MainPresenter
@Inject
constructor(private val dataManager: DataManager) : MainContract.Presenter() {

    private var subscription: Subscription? = null

    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }

    override fun loadRibots() {
        subscription?.unsubscribe()
        subscription = dataManager.getRibots()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext = { if (it.isEmpty()) view.showRibotsEmpty() else view.showRibots(it) },
                    onError = {
                        Timber.e(it, "There was an error loading the ribots.")
                        view.showError()
                    }
                )
    }
}
