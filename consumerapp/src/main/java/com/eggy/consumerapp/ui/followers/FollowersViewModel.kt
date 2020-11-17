package com.eggy.consumerapp.ui.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eggy.consumerapp.data.Constants.API_KEY
import com.eggy.consumerapp.data.Constants.BASE_URL
import com.eggy.consumerapp.data.entity.Followers
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowersViewModel : ViewModel() {
    private val listFollowersImmutable = ArrayList<Followers>()
    private val listFollowers = MutableLiveData<ArrayList<Followers>>()


    fun getFollowers(): LiveData<ArrayList<Followers>> {
        return listFollowers

    }

    fun getFollowersDetail(username: String) {

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
                    val follower = JSONObject(result)
                    val followersItems = Followers()
                    followersItems.username = follower.getString("login")
                    followersItems.avatar = follower.getString("avatar_url")
                    followersItems.name = follower.getString("name")

                    listFollowersImmutable.add(followersItems)
                    listFollowers.postValue(listFollowersImmutable)

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

    fun getFollowersData(username: String) {


        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $API_KEY")
        client.addHeader("User-Agent", "request")
        val url = "${BASE_URL}users/$username/followers"
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
                        getFollowersDetail(usernameGithub)
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