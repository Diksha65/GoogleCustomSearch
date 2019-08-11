package com.example.dunzoassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.example.dunzoassignment.model.FinalObject
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_list.*
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class ListActivity : AppCompatActivity() {

    private val searchTag = "Search"
    private var searchText : String?= null

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
            itemAnimator = DefaultItemAnimator()

            //removes the blink on the image on clicking the view holder
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            adapter = createAdapter()
        }
    }

    fun attachOnScrollListener(recyclerView: RecyclerView) {

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItems = layoutManager.itemCount
                val visibleItems = layoutManager.childCount
                val firstVisible = layoutManager.findFirstVisibleItemPosition()

                if(totalItems != 0 &&
                        visibleItems != 0 &&
                        firstVisible + visibleItems >= totalItems) {

                    logDebug("Reached bottom of Recycler view")
                    if(!requestSent.getAndSet(true) && hasMoreImages.get()) {
                        googleSearch(searchText!!, startIndex.toString())
                    } else {
                        notifyUser(
                            if(hasMoreImages.get()) "Ongoing Request" else "No more images from the API"
                        )
                    }
                } else {
                    logDebug("Total or visible items 0")
                }
            }
        })
    }

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


    fun googleSearch(
                context: Context,
                searchString: String,
                startIndex: String
            ) {
                val request =
                    createSearchRequest(searchURL(searchString, startIndex))

                HttpClient.client
                    .newCall(request)
                    .enqueue(object : Callback {

                        override fun onResponse(response: Response?) {

                            if (!response!!.isSuccessful)
                                logDebug("Unexpected code $response")

                            val json = response.body().string()

                            if (json == null)
                                logError(Error("Response is null"))
                            else {
                                val list = getImageList(json)
                                list?.let {
                                    runOnUiThread {
                                        (recyclerview.adapter as ImageAdapter).apply {
                                            addImages(it)
                                            notifyDataSetChanged()
                                        }
                                    }
                                }
                            }
                        }

                        override fun onFailure(request: Request?, e: IOException?) {
                            toast(context, "Failed to preocess your search!! Please try again later.")
                        }
                    })

            }
        }
    }
}

