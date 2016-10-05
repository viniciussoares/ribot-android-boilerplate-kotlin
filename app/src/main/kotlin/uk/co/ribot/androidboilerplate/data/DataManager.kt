package uk.co.ribot.androidboilerplate.data

import javax.inject.Inject
import javax.inject.Singleton

import rx.Observable
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper
import uk.co.ribot.androidboilerplate.data.model.Ribot
import uk.co.ribot.androidboilerplate.data.remote.RibotsService

@Singleton
class DataManager @Inject constructor(private val ribotsService: RibotsService,
                                      private val databaseHelper: DatabaseHelper) {

    fun syncRibots(): Observable<Ribot> {
        return ribotsService.getRibots()
                .concatMap { databaseHelper.setRibots(it) }
    }

    fun getRibots(): Observable<List<Ribot>> {
        return databaseHelper.getRibots().distinct()
    }
}
