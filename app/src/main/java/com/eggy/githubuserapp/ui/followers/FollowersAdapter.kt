package com.eggy.githubuserapp.ui.followers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eggy.githubuserapp.R
import com.eggy.githubuserapp.data.entity.Followers
import com.eggy.githubuserapp.data.entity.User
import kotlinx.android.synthetic.main.item_followers.view.*

class FollowersAdapter(private val listFollower:ArrayList<Followers>):RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    fun setFollowers(items: ArrayList<Followers>) {
        listFollower.clear()
        listFollower.addAll(items)
        notifyDataSetChanged()
    }
    inner class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followers: Followers){
            with(itemView){
                tv_name_followers.text = followers.name
                tv_username_followers.text = followers.username
                Glide.with(itemView.context)
                    .load(followers.avatar)
                    .into(iv_avatar_followers)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_followers, parent, false)
        return FollowersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(listFollower[position])
    }

    override fun getItemCount(): Int = listFollower.size
}