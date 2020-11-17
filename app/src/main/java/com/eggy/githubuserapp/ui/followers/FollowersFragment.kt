package com.eggy.githubuserapp.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eggy.githubuserapp.R
import com.eggy.githubuserapp.data.entity.Followers
import com.eggy.githubuserapp.data.entity.User
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    companion object{
        const val DATA = "data"
    }
    private val listFollowers:ArrayList<Followers> = arrayListOf()
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var adapter: FollowersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter(listFollowers)

        val dataFollowers = activity?.intent?.getParcelableExtra<User>(DATA) as User
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]

        getDataList(dataFollowers.username)
        getDataList()
        setUpViewModel(adapter)
        showList()




    }

    private fun getDataList() {
        followersViewModel.getFollowers()
        showLoading(true)

    }
    private fun showList() {
        recycler_view_followers.setHasFixedSize(true)
        recycler_view_followers.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()
        recycler_view_followers.adapter = adapter
    }

    private fun getDataList(username:String) {
        followersViewModel.getFollowersData(username)

    }
    private fun setUpViewModel(adapter: FollowersAdapter) {
        followersViewModel.getFollowers().observe(this, Observer {
            if (it != null) {
                adapter.setFollowers(it)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state:Boolean){
        if (state){
            progress_bar_followers.visibility = View.VISIBLE
        }else{
            progress_bar_followers.visibility = View.GONE
        }
    }


}