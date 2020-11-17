package com.eggy.consumerapp.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Following(

    var name:String ="",
    var username:String="",
    var avatar:String =""

):Parcelable