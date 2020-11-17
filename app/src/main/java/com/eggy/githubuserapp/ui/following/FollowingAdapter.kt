package com.eggy.githubuserapp.ui.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eggy.githubuserapp.R
import com.eggy.githubuserapp.data.entity.Followers
import com.eggy.githubuserapp.data.entity.Following
import kotlinx.android.synthetic.main.item_following.view.*

class FollowingAdapter(private val listFollowing:ArrayList<Following>):RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    fun setFollowing(items: ArrayList<Following>) {
        listFollowing.clear()
        listFollowing.addAll(items)
        notifyDataSetChanged()
    }
    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(following: Following){
            with(itemView){
                tv_name_following.text = following.name
                tv_username_following.text = following.username
                Glide.with(itemView.context)
                    .load(following.avatar)
                    .into(iv_avatar_following)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_following, parent, false)
        return  FollowingViewHolder(view)

    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int = listFollowing.size
}