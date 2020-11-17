package com.eggy.consumerapp.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eggy.consumerapp.data.Constants.API_KEY
import com.eggy.consumerapp.data.Constants.BASE_URL
import com.eggy.consumerapp.data.entity.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel:ViewModel() {
    val listUserLiveData = MutableLiveData<User>()

    fun getUserData(): MutableLiveData<User> {
        return listUserLiveData
    }

    fun getUserDetail(username: String) {

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $API_KEY")
        client.addHeader("User-Agent", "request")
        val url = "${BASE_URL}users/$username"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val user = JSONObject(result)
                    val userItems = User()
                    userItems.username = user.getString("login")
                    userItems.id = user.getInt("id")
                    userItems.avatar = user.getString("avatar_url")
                    userItems.name = user.getString("name")
                    userItems.location = user.getString("location")
                    userItems.repository = user.getInt("public_repos")
                    userItems.company = user.getString("company")
                    userItems.following = user.getInt("following")
                    userItems.follower = user.getInt("followers")

                    listUserLiveData.postValue(userItems)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())


                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }


                Log.d("onFailure", errorMessage)

            }

        })

    }
}