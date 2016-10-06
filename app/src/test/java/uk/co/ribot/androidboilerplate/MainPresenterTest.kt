package uk.co.ribot.androidboilerplate

import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import rx.Observable
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.data.model.Ribot
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory
import uk.co.ribot.androidboilerplate.ui.main.MainPresenter
import uk.co.ribot.androidboilerplate.util.RxSchedulersOverrideRule

import org.mockito.Mockito.anyListOf
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import uk.co.ribot.androidboilerplate.ui.main.MainContract

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Rule @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var mockMainMvpView: MainContract.View

    @Mock
    lateinit var mockDataManager: DataManager

    lateinit var mainPresenter: MainPresenter

    @Before
    fun setUp() {
        mainPresenter = MainPresenter(mockDataManager)
        mainPresenter.attachView(mockMainMvpView)
    }

    @After
    fun tearDown() {
        mainPresenter.detachView()
    }

    @Test
    fun loadRibotsReturnsRibots() {
        val ribots = TestDataFactory.makeListRibots(10)
        whenever(mockDataManager.getRibots()).thenReturn(Observable.just(ribots))

        mainPresenter.loadRibots()
        verify(mockMainMvpView).showRibots(ribots)
        verify(mockMainMvpView, never()).showRibotsEmpty()
        verify(mockMainMvpView, never()).showError()
    }

    @Test
    fun loadRibotsReturnsEmptyList() {
        whenever(mockDataManager.getRibots()).thenReturn(Observable.just(emptyList<Ribot>()))

        mainPresenter.loadRibots()
        verify(mockMainMvpView).showRibotsEmpty()
        verify(mockMainMvpView, never()).showRibots(anyListOf(Ribot::class.java))
        verify(mockMainMvpView, never()).showError()
    }

    @Test
    fun loadRibotsFails() {
        whenever(mockDataManager.getRibots()).
                thenReturn(Observable.error<List<Ribot>>(RuntimeException()))

        mainPresenter.loadRibots()
        verify(mockMainMvpView).showError()
        verify(mockMainMvpView, never()).showRibotsEmpty()
        verify(mockMainMvpView, never()).showRibots(anyListOf(Ribot::class.java))
    }
}
