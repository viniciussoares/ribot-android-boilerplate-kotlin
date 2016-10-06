package uk.co.ribot.androidboilerplate.ui.main

import javax.inject.Inject

import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.FunctionSubscriber
import rx.lang.kotlin.addTo
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.data.model.Ribot
import uk.co.ribot.androidboilerplate.injection.ConfigPersistent

@ConfigPersistent
class MainPresenter
@Inject
constructor(private val dataManager: DataManager) : MainContract.Presenter() {

    private val compositeSubscription = CompositeSubscription()

    override fun detachView() {
        super.detachView()
        compositeSubscription.clear()
    }

    override fun loadRibots() {
        dataManager.getRibots()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(FunctionSubscriber<List<Ribot>>()
                        .onNext {
                            if (it.isEmpty()) view.showRibotsEmpty() else view.showRibots(it)
                        }
                        .onError {
                            Timber.e(it, "There was an error loading the ribots.")
                            view.showError()
                        }
                ).addTo(compositeSubscription)
    }

}
