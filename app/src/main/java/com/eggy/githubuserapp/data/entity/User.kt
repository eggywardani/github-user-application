package com.eggy.githubuserapp.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    var id:Int = 0,
    var name:String ="",
    var username:String="",
    var avatar:String ="",
    var location:String? = null,
    var repository:Int = 0,
    var follower:Int = 0,
    var following:Int = 0,
    var company:String? = null

):Parcelable