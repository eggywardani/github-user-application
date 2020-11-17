package com.eggy.githubuserapp.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eggy.githubuserapp.R
import com.eggy.githubuserapp.data.entity.User
import com.eggy.githubuserapp.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_user_favorite.view.*

class FavoriteAdapter:RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavUser = ArrayList<User>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listFavUser.clear()
            }
            this.listFavUser.addAll(listNotes)
            notifyDataSetChanged()
        }



    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favUser: User) {
            with(itemView) {
                tv_name.text = favUser.username
                tv_username.text = favUser.name
                Glide.with(itemView.context)
                    .load(favUser.avatar)
                    .into(iv_avatar)

                itemView.setOnClickListener{

                    val goToDetail = Intent(itemView.context, DetailActivity::class.java)
                        .putExtra(DetailActivity.DATA, favUser)
                    itemView.context.startActivity(goToDetail)

                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        return holder.bind(listFavUser[position])
    }

    override fun getItemCount(): Int = listFavUser.size
}