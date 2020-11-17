package com.eggy.githubuserapp.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Followers(

    var name:String ="",
    var username:String="",
    var avatar:String =""

):Parcelable