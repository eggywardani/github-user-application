package com.eggy.githubuserapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter (private val listUser:List<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){



    class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        var tvName = itemView.tv_name
        var tvUsername = itemView.tv_username
        var ivAvatar = itemView.iv_avatar


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)

    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(70,70))
            .into(holder.ivAvatar)


        holder.tvName.text = user.name
        holder.tvUsername.text = user.username


        val myContext = holder.itemView.context
        holder.itemView.setOnClickListener {

            val goToDetail = Intent(myContext, DetailActivity::class.java)
                .putExtra(DetailActivity.DATA, user)
            myContext.startActivity(goToDetail)



        }

    }

}