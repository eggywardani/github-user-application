package com.eggy.consumerapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eggy.consumerapp.R
import com.eggy.consumerapp.data.entity.User
import com.eggy.consumerapp.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val listUser : ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    fun setUser(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            with(itemView) {

                tv_username.text = user.username
                tv_name.text = user.name


                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(iv_avatar)

                itemView.setOnClickListener{
                    val goToDetail = Intent(itemView.context, DetailActivity::class.java)
                        .putExtra(DetailActivity.DATA, user)
                    itemView.context.startActivity(goToDetail)

                }

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)

    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }



}