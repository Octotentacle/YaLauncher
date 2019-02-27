package octotentacle.yalauncher.apps

import android.content.Intent
import android.graphics.drawable.Drawable

data class AppInfo(
    val icon: Drawable,
    val name: String,
    val isUserApp: Boolean,
    val launchIntent: Intent,
    val deleteIntent: Intent,
    val infoIntent: Intent
)