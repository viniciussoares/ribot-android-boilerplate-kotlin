package uk.co.ribot.androidboilerplate.ui.main

import android.os.Bundle
import uk.co.ribot.androidboilerplate.R
import uk.co.ribot.androidboilerplate.data.SyncService
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    val EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent.getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true))
            startService(SyncService.getStartIntent(this))
    }
}
