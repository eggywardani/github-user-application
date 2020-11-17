package com.eggy.consumerapp.data.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {


    const val AUTHORITY = "com.eggy.githubuserapp"
    const val SCHEME = "content"
    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val _ID = "_id"
            const val NAME = "name"
            const val USERNAME = "username"
            const val AVATAR = "avatar"
            const val LOCATION = "location"
            const val REPOSITORY = "repository"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val COMPANY = "company"




            val CONTENT_URI:Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()




        }
    }
}