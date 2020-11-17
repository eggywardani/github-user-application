package com.eggy.consumerapp.helper

import android.database.Cursor
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWERS
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.eggy.consumerapp.data.db.DatabaseContract.FavoriteColumns.Companion._ID
import com.eggy.consumerapp.data.entity.User

object MappingHelper {
    fun cursorToArray(favCursor: Cursor?): ArrayList<User> {
        val listFavUser = ArrayList<User>()
        favCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val name = getString(getColumnIndexOrThrow(NAME))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val location = getString(getColumnIndexOrThrow(LOCATION))
                val repository = getInt(getColumnIndexOrThrow(REPOSITORY))
                val followers = getInt(getColumnIndexOrThrow(FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(FOLLOWING))
                val company = getString(getColumnIndexOrThrow(COMPANY))


                listFavUser.add(User(id, name, username, avatar, location,repository, followers, following,company ))
            }
        }
        return listFavUser
    }
}