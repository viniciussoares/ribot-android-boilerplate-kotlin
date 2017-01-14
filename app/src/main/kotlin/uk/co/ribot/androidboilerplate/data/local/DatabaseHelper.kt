package uk.co.ribot.androidboilerplate.data.local

import android.database.sqlite.SQLiteDatabase
import com.squareup.sqlbrite.BriteDatabase

import rx.Observable
import rx.lang.kotlin.observable
import timber.log.Timber
import uk.co.ribot.androidboilerplate.data.model.Ribot
import java.sql.SQLException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper @Inject constructor(val db: BriteDatabase) {

    fun setRibots(newRibots: Collection<Ribot>): Observable<Ribot> {
        return observable { subscriber ->
            if (subscriber.isUnsubscribed)
                return@observable

            val transaction = db.newTransaction()

            try {
                db.delete(Db.RibotProfileTable.TABLE_NAME, null)

                newRibots.forEach {
                    val result = db.insert(Db.RibotProfileTable.TABLE_NAME,
                            Db.RibotProfileTable.toContentValues(it.profile),
                            SQLiteDatabase.CONFLICT_REPLACE)
                    if (result >= 0) subscriber.onNext(it)
                }

                transaction.markSuccessful()
                subscriber.onCompleted()
            } catch (exception: SQLException) {
                Timber.e(exception)
            }

            transaction.end()
        }
    }

    fun getRibots(): Observable<List<Ribot>> {
        return db.createQuery(Db.RibotProfileTable.TABLE_NAME,
                "SELECT * FROM ${Db.RibotProfileTable.TABLE_NAME}")
                .mapToList { Ribot(Db.RibotProfileTable.parseCursor(it)) }
    }
}
