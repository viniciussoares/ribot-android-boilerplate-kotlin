package uk.co.ribot.androidboilerplate.util.extension

import android.content.Context
import uk.co.ribot.androidboilerplate.BoilerplateApplication
import uk.co.ribot.androidboilerplate.injection.component.ApplicationComponent

fun Context.getApplicationComponent(): ApplicationComponent {
    return (applicationContext as BoilerplateApplication).applicationComponent
}
