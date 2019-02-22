package octotentacle.yalauncher

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewPager
import android.view.View

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        val pageNum = mViewPager!!.currentItem
        if(pageNum < 3){
            mViewPager!!.setCurrentItem(pageNum + 1, true)
        } else {
            finish()
        }
    }

    private var mViewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
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
