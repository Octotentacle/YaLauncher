package octotentacle.yalauncher

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import octotentacle.yalauncher.fragments.FirstFragment
import octotentacle.yalauncher.fragments.FourthFragment
import octotentacle.yalauncher.fragments.SecondFragment
import octotentacle.yalauncher.fragments.ThirdFragment

class WelcomePageAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(p0: Int): Fragment {
        return when(p0){
            0-> FirstFragment()
            1-> SecondFragment()
            2-> ThirdFragment()
            3-> FourthFragment()
            else -> Fragment()
        }
    }

}