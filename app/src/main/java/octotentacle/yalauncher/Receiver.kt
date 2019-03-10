package octotentacle.yalauncher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

class Receiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        /*StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Log.d("DelReceiver", log)
            }
        }*/
        val pg = Uri.parse(intent.dataString)
        when(intent.action){
            Intent.ACTION_PACKAGE_ADDED -> return

            Intent.ACTION_PACKAGE_REMOVED -> LauncherActivity.remove(pg)
        }
    }


}