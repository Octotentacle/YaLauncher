package octotentacle.yalauncher

import android.content.ComponentName
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.*
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_launcher.*
import octotentacle.yalauncher.apps.GridItemDecoration
import octotentacle.yalauncher.apps.ItemGridAdapter
import octotentacle.yalauncher.apps.ItemListAdapter
import octotentacle.yalauncher.apps.AppInfo



class LauncherActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var listItems : RecyclerView
    private lateinit var gridItems : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_launcher)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
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
        menuInflater.inflate(R.menu.launcher, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {

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

    private fun initRecyclers() {
        val appList = getInstalledAppList()
        val shPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val spanCount = when(shPrefs.getBoolean("app_model_default", true)){
            true -> resources.getInteger(R.integer.model_default)
            false -> resources.getInteger(R.integer.model_solid)
        }
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
        val appsInfoList = ArrayList<AppInfo>()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val appsResolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA)
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
        val pack = Uri.parse("package:${resolveInfo.activityInfo.packageName}")
        val launchIntent = Intent(Intent.ACTION_MAIN)
        val infoIntent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

        infoIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        infoIntent.data = pack

        val removeIntent = Intent(Intent.ACTION_UNINSTALL_PACKAGE, pack)
        removeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        removeIntent.data = pack

        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        launchIntent.component =
            ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name)
        return AppInfo(icon, name, isUserApp(resolveInfo.activityInfo.packageName) , launchIntent, removeIntent, infoIntent)
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

    fun isUserApp(packageApp: String) : Boolean{
        var ai = packageManager.getApplicationInfo(packageApp, 0)
        var mask = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
        return (ai.flags and mask) == 0
    }
}
