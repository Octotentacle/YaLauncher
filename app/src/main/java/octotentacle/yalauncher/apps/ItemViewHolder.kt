package octotentacle.yalauncher.apps

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import octotentacle.yalauncher.LauncherActivity
import octotentacle.yalauncher.R
import octotentacle.yalauncher.views.SquareImageView


class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val icon: SquareImageView = itemView.findViewById(R.id.item_icon)
    private val name: TextView = itemView.findViewById(R.id.item_name)

    fun bind(app: LauncherActivity.AppInfo) {
        icon.setImageDrawable(app.icon)
        name.text = app.name
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        itemView.setOnClickListener(listener)
    }
}