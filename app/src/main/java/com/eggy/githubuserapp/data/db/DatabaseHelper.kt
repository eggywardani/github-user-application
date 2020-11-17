package com.eggy.githubuserapp.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWERS
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.eggy.githubuserapp.data.db.DatabaseContract.FavoriteColumns.Companion._ID

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbfavorite"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME (" +
                " $_ID INTEGER PRIMARY KEY," +
                " $NAME TEXT NOT NULL," +
                " $USERNAME TEXT NOT NULL," +
                " $AVATAR TEXT NOT NULL,"+
                " $LOCATION TEXT NOT NULL,"+
                " $REPOSITORY TEXT NOT NULL,"+
                " $FOLLOWERS TEXT NOT NULL,"+
                " $FOLLOWING TEXT NOT NULL,"+
                " $COMPANY TEXT NOT NULL)"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}