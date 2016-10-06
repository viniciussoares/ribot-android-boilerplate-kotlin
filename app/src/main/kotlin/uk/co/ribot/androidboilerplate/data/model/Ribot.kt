package uk.co.ribot.androidboilerplate.data.model

import android.content.ContentValues
import android.database.Cursor
import nz.bradcampbell.paperparcel.PaperParcel
import rx.functions.Func1
import uk.co.ribot.androidboilerplate.util.extension.getLong
import uk.co.ribot.androidboilerplate.util.extension.getString
import java.util.Date

@PaperParcel
data class Ribot(val profile: Profile) {

    companion object {

        @JvmStatic val TABLE = "ribotsProfile"

        @JvmStatic val MAPPER: Func1<Cursor, Ribot> = Func1 { cursor ->
            val profile = Profile(
                    email = cursor.getString("email"),
                    name = Name(cursor.getString("firstName"), cursor.getString("lastName")),
                    hexColor = cursor.getString("hexColor"),
                    dateOfBirth = Date(cursor.getLong("dateOfBirth")),
                    avatar = cursor.getString("avatar"),
                    bio = cursor.getString("bio"))

            Ribot(profile)
        }

        @JvmStatic fun toContentValues(ribot: Ribot): ContentValues {
            val values = ContentValues()
            values.put("email", ribot.profile.email)
            values.put("firstName", ribot.profile.name.first)
            values.put("lastName", ribot.profile.name.last)
            values.put("hexColor", ribot.profile.hexColor)
            values.put("dateOfBirth", ribot.profile.dateOfBirth.time)
            values.put("avatar", ribot.profile.avatar ?: "")
            values.put("bio", ribot.profile.bio ?: "")
            return values
        }
    }
}
