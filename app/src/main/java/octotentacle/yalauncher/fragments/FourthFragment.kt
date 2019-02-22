package octotentacle.yalauncher.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatRadioButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import octotentacle.yalauncher.R

class FourthFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fourth_layout, container, false)
        val shPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val cmodel = shPrefs.getBoolean("app_model_default", true)
        when(cmodel){
            true->view.findViewById<AppCompatRadioButton>(R.id.radio_button_model_default).isChecked = true
            false->view.findViewById<AppCompatRadioButton>(R.id.radio_button_model_solid).isChecked = true
        }

        val rGroup = view.findViewById<RadioGroup>(R.id.radio_group_model_type)
        rGroup.setOnCheckedChangeListener{
            _, checkedId -> when(checkedId){
            R.id.radio_button_model_default -> shPrefs.edit().putBoolean("app_model_default", true).apply()
            R.id.radio_button_model_solid -> shPrefs.edit().putBoolean("app_model_default", false).apply()
            }
        }

        return view
    }
}