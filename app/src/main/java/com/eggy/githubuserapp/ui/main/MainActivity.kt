package com.eggy.githubuserapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eggy.githubuserapp.R
import com.eggy.githubuserapp.data.entity.User
import com.eggy.githubuserapp.ui.favorite.FavoriteActivity
import com.eggy.githubuserapp.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var mainViewModel: MainViewModel
    private var list: ArrayList<User> = ArrayList()
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = UserAdapter(list)
        showList()
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        setUpViewModel(adapter)
        getDataList()
        showList()

    }


    private fun setUpViewModel(adapter: UserAdapter) {
        mainViewModel.getListUser().observe(this, {

            if (it != null) {
                list = it
                adapter.setUser(it)
                showLoading(false)


            }
        })
    }


    private fun showList() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter
    }

    private fun getDataList() {
        mainViewModel.getUserData()
        showLoading(true)

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchView = menu?.findItem(R.id.search_action)?.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    list.clear()
                    mainViewModel.searchDataUser(query)
                    setUpViewModel(adapter)
                    showLoading(true)

                } else {
                    return true
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.user_settings) {
            val settings = Intent(this, SettingsActivity::class.java)
            startActivity(settings)
        }else if (item.itemId == R.id.fav_user_setting){
            val fav = Intent(this, FavoriteActivity::class.java)
            startActivity(fav)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state:Boolean){
        if (state){
            progress_bar.visibility = View.VISIBLE
        }else{
            progress_bar.visibility = View.GONE
        }
    }


}
