package octotentacle.yalauncher

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.android.synthetic.main.app_bar_launcher.*
import octotentacle.yalauncher.apps.GridItemDecoration
import octotentacle.yalauncher.apps.ItemGridAdapter
import octotentacle.yalauncher.apps.ItemListAdapter

class LauncherActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var listItems : RecyclerView
    private lateinit var gridItems : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this::onNavigationItemSelected)
        initRecyclers()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.launcher, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    data class AppInfo(
        val icon: Drawable,
        val name: String,
        val launchIntent: Intent
    )

    private fun initRecyclers() {
        val appList = getInstalledAppList()
        val spanCount = 5
        listItems = findViewById(R.id.list_items)
        listItems.layoutManager = LinearLayoutManager(this)
        listItems.adapter = ItemListAdapter(this, appList)
        listItems.visibility = View.GONE
        gridItems = findViewById(R.id.grid_items)
        gridItems.layoutManager = GridLayoutManager(this, spanCount)
        gridItems.adapter = ItemGridAdapter(this, appList)
        val offset = resources.getDimensionPixelOffset(R.dimen.square_margin)
        gridItems.addItemDecoration(GridItemDecoration(offset))
        val dividerItemDecoration = DividerItemDecoration(this, (listItems.layoutManager as LinearLayoutManager).orientation)
        listItems.addItemDecoration(GridItemDecoration(offset))
        listItems.addItemDecoration(dividerItemDecoration)
    }

    private fun getInstalledAppList(): List<AppInfo> {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val appsResolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA)
        val appsInfoList = ArrayList<AppInfo>()
        for (it in appsResolveInfoList) {
            if (it.activityInfo.packageName == packageName)
                continue
            appsInfoList.add(getAppInfo(it))
        }
        return appsInfoList
    }

    private fun getAppInfo(resolveInfo: ResolveInfo): AppInfo {
        val icon = resolveInfo.loadIcon(packageManager)
        val name = resolveInfo.loadLabel(packageManager).toString()
        val launchIntent = Intent(Intent.ACTION_MAIN)
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        launchIntent.component =
            ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name)
        return AppInfo(icon, name, launchIntent)
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
