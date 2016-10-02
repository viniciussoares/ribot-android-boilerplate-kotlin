package uk.co.ribot.androidboilerplate.data.remote

import retrofit2.http.GET
import rx.Observable
import uk.co.ribot.androidboilerplate.data.model.Ribot

interface RibotsService {
    @GET("ribots")
    fun getRibots(): Observable<List<Ribot>>
}
