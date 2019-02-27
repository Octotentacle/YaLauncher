package octotentacle.yalauncher.apps

import android.content.Context
import android.util.Log
import android.view.ContextMenu

import android.view.MenuItem
import android.view.View
import octotentacle.yalauncher.R


class ItemContextMenu(val appInfo: AppInfo, val context: Context): View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            0 -> context.startActivity(appInfo.deleteIntent)
            1 -> context.startActivity(appInfo.infoIntent)
        }
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.setHeaderTitle(appInfo.name)
        if(appInfo.isUserApp){
            val deleteItem = menu?.add(0, 0, 0, R.string.menu_item_remove)
            deleteItem?.setOnMenuItemClickListener(this)

        }
        val infoItem = menu?.add(0, 1, 0, R.string.menu_item_info)
        infoItem?.setOnMenuItemClickListener(this)
    }

}