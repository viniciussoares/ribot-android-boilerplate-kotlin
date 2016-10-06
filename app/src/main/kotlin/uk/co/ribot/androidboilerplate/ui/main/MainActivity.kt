package uk.co.ribot.androidboilerplate.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import uk.co.ribot.androidboilerplate.R
import uk.co.ribot.androidboilerplate.data.SyncService
import uk.co.ribot.androidboilerplate.data.model.Ribot
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    val EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG"

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var ribotsAdapter: RibotsAdapter

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        recyclerView.adapter = ribotsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        presenter.attachView(this)
        presenter.loadRibots()

        if (intent.getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showRibots(ribots: List<Ribot>) {
        ribotsAdapter.ribots = ribots
        ribotsAdapter.notifyDataSetChanged()
    }

    override fun showRibotsEmpty() {
        ribotsAdapter.ribots = emptyList()
        ribotsAdapter.notifyDataSetChanged()
        Toast.makeText(this, R.string.empty_ribots, Toast.LENGTH_LONG).show()
    }

    override fun showError() {
        Toast.makeText(this, R.string.error_loading_ribots, Toast.LENGTH_LONG).show()
    }
}
