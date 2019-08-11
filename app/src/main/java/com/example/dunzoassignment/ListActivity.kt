package com.example.dunzoassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.example.dunzoassignment.model.Feed
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_list.*
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class ListActivity : AppCompatActivity() {

    private val searchTag = "Search"
    private var searchText: String? = null

    //Atomic values are used since many threads are calling it.
    private var startIndex = AtomicInteger(1)
    private var requestSent = AtomicBoolean(false)
    private var hasMoreImages = AtomicBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        searchText = intent.getStringExtra(searchTag)!!
        googleSearch(searchText!!)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            //removes the blink on the image on clicking the view holder
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            adapter = createAdapter()

            attachOnScrollListener(recyclerview)
        }
    }

    //Attaching a scroll view on the recycler view
    fun attachOnScrollListener(recyclerView: RecyclerView) {

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItems = layoutManager.itemCount
                val visibleItems = layoutManager.childCount
                val firstVisible = layoutManager.findFirstVisibleItemPosition()

                if (totalItems != 0 &&
                    visibleItems != 0 &&
                    firstVisible + visibleItems >= totalItems
                ) {

                    logDebug("Reached bottom of Recycler view")
                    if (!requestSent.getAndSet(true) && hasMoreImages.get()) {
                        googleSearch(searchText!!, startIndex.toString())
                    } else {
                        notifyUser(
                            if (hasMoreImages.get()) "Ongoing Request" else "No more images from the API"
                        )
                    }
                } else {
                    logDebug("Total or visible items 0")
                }
            }
        })
    }

    //Notifying users about the updates by running on UI thread
    private fun notifyUser(message: String) {
        runOnUiThread {
            toast(this@ListActivity, message)
        }
    }

    //creating Adapter for recycler view by calling the callback function for details button
    fun createAdapter(): ImageAdapter {
        return ImageAdapter().apply {

            addDetailButtonCallback { finalObject ->
                val intent = Intent(this@ListActivity, DetailActivity::class.java)

                val bundle = Bundle().apply { putSerializable("ImageObject", finalObject) }
                intent.putExtras(bundle)

                startActivity(intent)
            }
        }
    }

    //checks if the response is valid or not
    fun didAPIsucceed(response: Response?): Boolean {

        if (response == null) {
            logError(Error("No response"))
            return false
        }

        response.apply {
            if (!isSuccessful) {
                logError(Error("Unsuccessful Response"))
                return false
            }

            if (code() == 403) {
                notifyUser("Internet issue. Connection lost")
                return false
            }

            if (code() != 200) {
                notifyUser("Quota Over with code ${code()}")
                return false
            }
        }

        return true
    }

    //returns the start index for the next request. returns null for no request.
    fun setStartIndexFrom(feed: Feed) {

        //Assuming that if the startIndex of nextPage is NULL then there are no more requests to be made
        val start = feed.queries?.nextPage?.get(0)?.startIndex

        if (start == null) {
            notifyUser("No more images from the API")
            hasMoreImages.set(false)
        } else {
            startIndex.set(start)
        }
    }

    //calls the OkHttp api
    fun googleSearch(searchString: String, startIndex: String = "1") {
        val request =
            createSearchRequest(searchURL(searchString, startIndex))

        HttpClient.client.newCall(request).enqueue(object : Callback {

            override fun onResponse(response: Response?) {
                val json = response?.body()?.string()
                if (didAPIsucceed(response) && json != null) {
                    handleResponse(json)
                } else {
                    logError(Error("API Failure"))
                    requestSent.set(false)
                }
            }

            override fun onFailure(request: Request?, e: IOException?) {
                notifyUser("Failed to process your search!! Please try again later. ")
                requestSent.set(false)
            }
        })
    }

    fun handleResponse(json : String) {

        val feed = toFeed(json)

        if(feed != null) {

            setStartIndexFrom(feed)
            val count = feed.queries?.request?.get(0)?.count

            if(count != null && count != 0) {

                val images = getImageList(json)

                if(images == null || images.size == 0) {
                    hasMoreImages.set(false)
                } else {
                    runOnUiThread {
                        (recyclerview.adapter as ImageAdapter).apply {
                            addImages(images)
                            notifyDataSetChanged()
                            requestSent.set(false)
                        }
                    }
                }

            } else {
                hasMoreImages.set(false)
                notifyUser("No more images from the API")
            }

        } else {
            hasMoreImages.set(false)
            notifyUser("No more images from the API")
        }
    }
}
