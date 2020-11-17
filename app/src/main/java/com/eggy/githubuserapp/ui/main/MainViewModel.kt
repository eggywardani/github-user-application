package com.eggy.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eggy.githubuserapp.data.Constants.API_KEY
import com.eggy.githubuserapp.data.Constants.BASE_URL
import com.eggy.githubuserapp.data.entity.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val listUserImmutable = ArrayList<User>()
    private val listUser = MutableLiveData<ArrayList<User>>()


    fun getListUser(): LiveData<ArrayList<User>> {
        return listUser

    }


    fun getUserData() {

        val url = "${BASE_URL}users"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $API_KEY")
        client.addHeader("User-Agent", "request")
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
                        val username = user.getString("login")
                        getUserDetail(username)
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

    fun searchDataUser(query: String) {
        val url = "${BASE_URL}search/users?q=$query"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $API_KEY")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                    val items = responseObject.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val user = items.getJSONObject(i)
                        val username = user.getString("login")
                        getUserDetail(username)


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

    fun getUserDetail(username: String) {

        val url = "${BASE_URL}users/$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $API_KEY")
        client.addHeader("User-Agent", "request")
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
                    userItems.id = user.getInt("id")
                    userItems.username = user.getString("login")
                    userItems.avatar = user.getString("avatar_url")
                    userItems.name = user.getString("name")
                    userItems.location = user.getString("location")
                    userItems.repository = user.getInt("public_repos")
                    userItems.company = user.getString("company")
                    userItems.following = user.getInt("following")
                    userItems.follower = user.getInt("followers")

                    listUserImmutable.add(userItems)
                    listUser.postValue(listUserImmutable)

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