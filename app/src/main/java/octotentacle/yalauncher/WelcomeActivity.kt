package octotentacle.yalauncher

import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewPager
import android.view.View
import com.crashlytics.android.Crashlytics
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import io.fabric.sdk.android.Fabric

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        val pageNum = mViewPager!!.currentItem
        if(pageNum < 3){
            mViewPager!!.setCurrentItem(pageNum + 1, true)
        } else {
            finish()
            val nextInt = Intent(this, LauncherActivity::class.java).apply{
                putExtra(EXTRA_MESSAGE, "")
            }
            startActivity(nextInt)
        }
    }

    private var mViewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCenter.start(
            application, "78320121-e28f-4703-a7f1-c8da1bb912ab",
            Analytics::class.java, Crashes::class.java, Distribute::class.java)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)
        mViewPager = findViewById(R.id.welcome_page_view_pager)
        mViewPager!!.adapter = WelcomePageAdapter(supportFragmentManager)
        val fab: FloatingActionButton = findViewById(R.id.welcome_page_fab_next)
        fab.setOnClickListener {v->onClick(v)}
    }

    override fun onBackPressed() {
        val viewPagerTabNum = mViewPager!!.currentItem
        if (viewPagerTabNum > 0) {
            mViewPager!!.setCurrentItem(viewPagerTabNum - 1, true)
        }
    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        val shPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val dark = shPrefs.getBoolean("app_theme_dark", false)
        if (!dark) {
            theme.applyStyle(R.style.AppThemeLight, true)
        } else theme.applyStyle(R.style.AppThemeDark, true)
        return theme
    }
}
