package octotentacle.yalauncher.apps

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import octotentacle.yalauncher.R


class ItemGridAdapter(
    private val context: Context,
    private val appList: List<AppInfo>
) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun getItemCount(): Int = appList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(appList[position])
        holder.setOnClickListener(View.OnClickListener {
            context.startActivity(appList[position].launchIntent)
        })
        holder.itemView.setOnCreateContextMenuListener(ItemContextMenu(appList[position], context))
    }
}