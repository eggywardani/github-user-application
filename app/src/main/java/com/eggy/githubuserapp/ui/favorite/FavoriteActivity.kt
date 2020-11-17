package com.eggy.githubuserapp.ui.favorite

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eggy.githubuserapp.R
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.eggy.githubuserapp.data.entity.User
import com.eggy.githubuserapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
    private lateinit var adapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.apply {
            title = getString(R.string.favorite)
            setDisplayHomeAsUpEnabled(true)
        }
        setAdapter()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadDataAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        if (savedInstanceState == null) {
            loadDataAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavUser = list
            }
        }



    }

    private fun setAdapter(){
        recycler_view_favorite.layoutManager = LinearLayoutManager(this)
        recycler_view_favorite.setHasFixedSize(true)
        adapter = FavoriteAdapter()
        recycler_view_favorite.adapter = adapter

    }

    private fun loadDataAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.cursorToArray(cursor)
            }
            val fav = deferredNotes.await()
            if (fav.size > 0) {
                adapter.listFavUser = fav
                isEmptyData(true)
            } else {
                adapter.listFavUser = ArrayList()
                isEmptyData(false)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavUser)
    }

    private fun isEmptyData(state:Boolean){
        when(state){
            true -> {
                iv_empty.visibility = View.GONE
                tv_empty.visibility = View.GONE
            }
            else -> {
                iv_empty.visibility = View.VISIBLE
                tv_empty.visibility = View.VISIBLE
            }
        }

    }


}