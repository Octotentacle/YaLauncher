package octotentacle.yalauncher.fragments

import android.content.SharedPreferences
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import android.widget.RadioButton
import android.widget.RadioGroup

import octotentacle.yalauncher.R

class ThirdFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener{

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key == "app_theme_dark") activity?.recreate()
    }

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.third_layout, container, false)
        val dark = view.findViewById<RadioButton>(R.id.radio_button_theme_dark)
        val light = view.findViewById<RadioButton>(R.id.radio_button_theme_light)
        val shPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        dark.isChecked = shPrefs.getBoolean("app_theme_dark", false)
        light.isChecked = !dark.isChecked
        val rgTheme = view.findViewById<RadioGroup>(R.id.radio_group_theme)

        rgTheme.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.radio_button_theme_light)
                shPrefs.edit().putBoolean("app_theme_dark", false).apply()
            else
                shPrefs.edit().putBoolean("app_theme_dark", true).apply()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(this)
    }
}


