package uk.co.ribot.androidboilerplate

import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import rx.Observable
import rx.observers.TestSubscriber
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper
import uk.co.ribot.androidboilerplate.data.model.Ribot
import uk.co.ribot.androidboilerplate.data.remote.RibotsService
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory

import org.mockito.Mockito.anyListOf

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {

    @Mock
    lateinit var mockDatabaseHelper: DatabaseHelper

    @Mock
    lateinit var mockRibotsService: RibotsService

    lateinit var dataManager: DataManager

    @Before
    fun setUp() {
        dataManager = DataManager(mockRibotsService, mockDatabaseHelper)
    }

    @Test
    fun syncRibotsEmitsValues() {
        val ribots = listOf(TestDataFactory.makeRibot("r1"), TestDataFactory.makeRibot("r2"))
        stubSyncRibotsHelperCalls(ribots)

        val result = TestSubscriber<Ribot>()
        dataManager.syncRibots().subscribe(result)
        result.assertNoErrors()
        result.assertReceivedOnNext(ribots)
    }

    @Test
    fun syncRibotsCallsApiAndDatabase() {
        val ribots = listOf(TestDataFactory.makeRibot("r3"), TestDataFactory.makeRibot("r4"))
        stubSyncRibotsHelperCalls(ribots)

        dataManager.syncRibots().subscribe()
        // Verify right calls to helper methods
        verify(mockRibotsService).getRibots()
        verify(mockDatabaseHelper).setRibots(ribots)
    }

    @Test
    fun syncRibotsDoesNotCallDatabaseWhenApiFails() {
        whenever(mockRibotsService.getRibots()).thenReturn(Observable.error<List<Ribot>>(RuntimeException()))

        dataManager.syncRibots().subscribe(TestSubscriber<Ribot>())
        // Verify right calls to helper methods
        verify(mockRibotsService).getRibots()
        verify(mockDatabaseHelper, never()).setRibots(anyListOf(Ribot::class.java))
    }

    private fun stubSyncRibotsHelperCalls(ribots: List<Ribot>) {
        // Stub calls to the ribot service and database helper.
        whenever(mockRibotsService.getRibots()).thenReturn(Observable.just(ribots))
        whenever(mockDatabaseHelper.setRibots(ribots)).thenReturn(Observable.from(ribots))
    }
}
