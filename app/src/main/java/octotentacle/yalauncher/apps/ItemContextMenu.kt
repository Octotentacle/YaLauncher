package octotentacle.yalauncher.apps

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ContextMenu

import android.view.MenuItem
import android.view.View
import octotentacle.yalauncher.LauncherActivity
import octotentacle.yalauncher.R


class ItemContextMenu(val appInfo: AppInfo, val context: Context, val pos: Int): View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    companion object {
        var rmp = -1
    }
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