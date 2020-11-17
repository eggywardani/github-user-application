package com.eggy.consumerapp.ui.detail

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.eggy.consumerapp.R
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWERS
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion._ID
import com.eggy.consumerapp.data.entity.User
import com.eggy.consumerapp.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.detail_user.*
import kotlinx.android.synthetic.main.detail_user.tv_name
import kotlinx.android.synthetic.main.detail_user_2.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DATA = "data"
    }

    private lateinit var data: User
    private var values = ContentValues()
    private var isFavorite = true
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var uriWithId: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)



        data = intent.getParcelableExtra<User>(DATA) as User
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + data.id)
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]



        getDataList(data.username)



        setupViewPager()
        supportActionBar?.apply {
            title = data.username
            elevation = 0F
            setDisplayHomeAsUpEnabled(true)
        }

        initView()


        btn_fav.setOnClickListener {
            if (isFavorite) {
                contentResolver.delete(uriWithId, null, null)
                setFavorite(false)
            } else {
                values.put(_ID, data.id.toString())
                values.put(NAME, data.name)
                values.put(USERNAME, data.username)
                values.put(AVATAR, data.avatar)
                values.put(LOCATION, data.location)
                values.put(REPOSITORY, data.repository)
                values.put(FOLLOWERS, data.follower)
                values.put(FOLLOWING, data.following)
                values.put(COMPANY, data.company)
                contentResolver.insert(CONTENT_URI, values)

                setFavorite(true)
            }

        }


    }




    private fun getDataList(username: String) {
        detailViewModel.getUserDetail(username)
        detailViewModel.getUserData().observe(this, {
            if (it != null){
                setDataList()
            }
        })


    }


    private fun setDataList() {
        tv_name.text = data.name
        tv_company.text = data.company
        tv_amount_follower.text = data.follower.toString()
        tv_amount_following.text = data.following.toString()
        tv_amount_repository.text = data.repository.toString()
        tv_location.text = data.location

        Glide.with(this)
            .load(data.avatar)
            .into(img_avatar)
    }



    @SuppressLint("Recycle")
    private fun initView() {
        val cursor = contentResolver.query(uriWithId, null, null, null, null) as Cursor
        if (cursor.moveToNext()) {
            setFavorite(true)
        } else {
            setFavorite(false)
        }
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

    private fun setFavorite(state: Boolean) {
        when(state){
            true -> {
                isFavorite = true
                btn_fav.background = ContextCompat.getDrawable(this, R.drawable.bg_unfav)
                btn_fav.text = getString(R.string.unfavorite)
            }
            else ->{

                isFavorite = false
                btn_fav.background = ContextCompat.getDrawable(this, R.drawable.bg_fav)
                btn_fav.text = getString(R.string.favorite)

            }
        }
    }




}