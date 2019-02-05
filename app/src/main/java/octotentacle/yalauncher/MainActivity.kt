package octotentacle.yalauncher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCenter.start(
            application, "78320121-e28f-4703-a7f1-c8da1bb912ab",
            Analytics::class.java, Crashes::class.java)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)
    }
}
