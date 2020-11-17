package com.eggy.consumerapp.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eggy.consumerapp.data.Constants.API_KEY
import com.eggy.consumerapp.data.Constants.BASE_URL
import com.eggy.consumerapp.data.entity.Following
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowingViewModel : ViewModel() {
    private val listFollowingImmutable = ArrayList<Following>()
    private val listFollowing = MutableLiveData<ArrayList<Following>>()


    fun getFollowers(): LiveData<ArrayList<Following>> {
        return listFollowing
    }

    fun getFollowingDetail(username: String) {

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
                    val following = JSONObject(result)
                    val followingItems = Following()
                    followingItems.username = following.getString("login")
                    followingItems.avatar = following.getString("avatar_url")
                    followingItems.name = following.getString("name")

                    listFollowingImmutable.add(followingItems)
                    listFollowing.postValue(listFollowingImmutable)


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

    fun getFollowingData(username: String) {


        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $API_KEY")
        client.addHeader("User-Agent", "request")
        val url = "${BASE_URL}users/$username/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val user = responseArray.getJSONObject(i)
                        val usernameGithub = user.getString("login")
                        getFollowingDetail(usernameGithub)
                    }
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