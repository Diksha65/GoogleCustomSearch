package com.example.dunzoassignment.searchapi

import android.content.Context
import com.example.dunzoassignment.logDebug
import com.example.dunzoassignment.logError
import com.example.dunzoassignment.model.Feed
import com.example.dunzoassignment.toast
import com.google.gson.GsonBuilder
import com.squareup.okhttp.*
import java.io.IOException

object HttpClient {
    val client : OkHttpClient
        get() = OkHttpClient()
}

fun searchURL(searchString: String, startIndex : String) : HttpUrl {

    return HttpUrl.parse("https://www.googleapis.com/customsearch/v1")
        .newBuilder()
        .addQueryParameter("q", searchString)
        .addQueryParameter("cx", "011476162607576381860:ra4vmliv9ti")
        .addQueryParameter("key", "AIzaSyDpMYRjzmp67tQoGDmCk8iun_rY657Lefs")
        .addQueryParameter("start", startIndex)
        .build()
}

fun createSearchRequest(url : HttpUrl) : Request {
    return Request.Builder()
        .url(url)
        .build()
}

fun handleSearchResponse(response: Response) {
    val responseBody = response.body().string()
    val gson = GsonBuilder().create()
    gson.fromJson(responseBody, Feed::class.java)
}

fun googleSearch(context: Context, searchString: String, startIndex : String, onResposeReceived : () -> Unit ) {

    val request = createSearchRequest(searchURL(searchString, startIndex))

    HttpClient.client
        .newCall(request)
        .enqueue(object : Callback {
            @Throws(Exception::class)
            override fun onResponse(response: Response?) {
                try {
                    if(!response!!.isSuccessful)
                        throw IOException("Unexpected code $response")

                    logDebug(response.body().string())
                    handleSearchResponse(response)
                    onResposeReceived()

                } catch (e : IOException) {
                    logError(Error("Error occured while handling response. ${e.printStackTrace()}"))
                }
            }

            override fun onFailure(request: Request?, e: IOException?) {
                toast(context, "Failed to preocess your search!! Please try again later.")
            }
        })
}