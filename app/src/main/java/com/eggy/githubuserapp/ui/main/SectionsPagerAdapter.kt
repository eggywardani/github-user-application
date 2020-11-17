package com.eggy.githubuserapp.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.eggy.githubuserapp.ui.followers.FollowersFragment
import com.eggy.githubuserapp.ui.following.FollowingFragment
import com.eggy.githubuserapp.R

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val title = arrayOf(R.string.tab_followers, R.string.tab_following)
    override fun getCount(): Int {
        return title.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment()
            else -> FollowingFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(title[position])
    }

      
}