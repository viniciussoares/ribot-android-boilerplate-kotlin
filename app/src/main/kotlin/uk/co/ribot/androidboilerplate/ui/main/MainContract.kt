package uk.co.ribot.androidboilerplate.ui.main

import uk.co.ribot.androidboilerplate.data.model.Ribot
import uk.co.ribot.androidboilerplate.ui.base.BaseMvpPresenter
import uk.co.ribot.androidboilerplate.ui.base.MvpView

object MainContract {

    interface View: MvpView {
        fun showRibots(ribots: List<Ribot>)
        fun showRibotsEmpty()
        fun showError()
    }

    abstract class Presenter: BaseMvpPresenter<View>() {
        abstract fun loadRibots()
    }
}
