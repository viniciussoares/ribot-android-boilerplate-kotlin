package uk.co.ribot.androidboilerplate.ui.main

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import uk.co.ribot.androidboilerplate.R
import uk.co.ribot.androidboilerplate.data.model.Ribot

class RibotsAdapter
@Inject
constructor() : RecyclerView.Adapter<RibotsAdapter.RibotViewHolder>() {

    var ribots = emptyList<Ribot>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RibotViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ribot, parent, false)
        return RibotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RibotViewHolder, position: Int) {
        val ribot = ribots[position]

        holder.hexColorView.setBackgroundColor(Color.parseColor(ribot.profile.hexColor))
        holder.nameTextView.text = "${ribot.profile.name.first} ${ribot.profile.name.last}"
        holder.emailTextView.text = ribot.profile.email
    }

    override fun getItemCount(): Int {
        return ribots.size
    }

    inner class RibotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.view_hex_color)
        lateinit var hexColorView: View

        @BindView(R.id.text_name)
        lateinit var nameTextView: TextView

        @BindView(R.id.text_email)
        lateinit var emailTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
