package com.eggy.githubuserapp.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eggy.githubuserapp.R
import com.eggy.githubuserapp.data.entity.Following
import com.eggy.githubuserapp.data.entity.User
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    companion object{
        const val DATA = "data"
    }
    private val listFollowing:ArrayList<Following> = arrayListOf()
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: FollowingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = FollowingAdapter(listFollowing)

        val dataFollowing = activity?.intent?.getParcelableExtra<User>(DATA) as User
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]

        getDataList(dataFollowing.username)
        getDataList()
        setUpViewModel(adapter)
        showList()
    }

    private fun getDataList() {
        followingViewModel.getFollowers()
        showLoading(true)

    }
    private fun showList() {
        recycler_view_following.setHasFixedSize(true)
        recycler_view_following.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()
        recycler_view_following.adapter = adapter
    }

    private fun getDataList(username:String) {
        followingViewModel.getFollowingData(username)

    }
    private fun setUpViewModel(adapter: FollowingAdapter) {
        followingViewModel.getFollowers().observe(this, Observer {
            if (it != null) {
                adapter.setFollowing(it)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state:Boolean){
        if (state){
            progress_bar_following.visibility = View.VISIBLE
        }else{
            progress_bar_following.visibility = View.GONE
        }
    }


}