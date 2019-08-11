package com.example.dunzoassignment

import com.example.dunzoassignment.model.Feed
import com.example.dunzoassignment.model.FinalObject
import com.google.gson.GsonBuilder
import com.squareup.okhttp.*

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

    //AIzaSyBhr-u_ydyht1fHWoQXyTaTgR3nRJc1J1o
    //AIzaSyCybp92CY-GYqvqRDsHFVoiBpzURAdPqys
    //AIzaSyAcN29jb5LOVBvMTWXGe5ie6X7D9HS68tY
}

fun createSearchRequest(url : HttpUrl) : Request {
    return Request.Builder()
        .url(url)
        .build()
}

fun toFeed(json : String) : Feed? {
    val gson = GsonBuilder().create()
    val feed = gson.fromJson(json, Feed::class.java)

    if(feed == null)
        logError(Error("No response received"))

    return feed
}

fun getImageList(json : String) = getImageList(toFeed(json))

fun getImageList(feed : Feed?) : ArrayList<FinalObject>? {

    return feed?.items?.mapNotNullTo(arrayListOf()) {
        if(it.pagemap != null &&
                it.pagemap.cseThumbnail != null &&
                it.pagemap.cseThumbnail.size > 0 &&
                it.pagemap.cseImage != null &&
                it.pagemap.cseImage.size >0 ) {
            FinalObject (
                it.pagemap.cseThumbnail[0].src,
                it.pagemap.cseImage[0].src,
                it.title,
                it.link,
                it.pagemap.metatags[0].ogDescription,
                false
            )
        } else {
            null
        }
    }
}

