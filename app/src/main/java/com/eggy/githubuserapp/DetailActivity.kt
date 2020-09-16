package com.eggy.githubuserapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.detail_user.*
import kotlinx.android.synthetic.main.detail_user_2.*
import kotlinx.android.synthetic.main.item_user.tv_name

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.elevation = 0F
        val data = intent.getParcelableExtra<User>(DATA)

        tv_name.text = data?.name
        tv_company.text = data?.company
        tv_amount_follower.text = data?.follower.toString()
        tv_amount_following.text = data?.following.toString()
        tv_amount_repository.text = data?.repository.toString()
        tv_location.text = data?.location

        Glide.with(this)
            .load(data?.avatar)
            .into(img_avatar)



        setupViewPager()
        supportActionBar?.title = data?.username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

    }

}