package com.example.dunzoassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dunzoassignment.model.FinalObject
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_list.*
import java.io.IOException
import androidx.recyclerview.widget.SimpleItemAnimator



class ListActivity : AppCompatActivity() {

    private val searchTag = "Search"
    private val startIndexTag = "startIndex"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val searchText = intent.getStringExtra(searchTag)
        val startindex = intent.getStringExtra(startIndexTag)

        if(searchText != null) {
            googleSearch(this, searchText, startindex!!)

        } else {
            toast(this, "You did not search for anything.")
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            //removes the blink on the image on clicking the view holder
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            adapter = ImageAdapter().apply {
                addDetailButtonCallback { finalObject ->
                    val intent = Intent(this@ListActivity, DetailActivity::class.java)

                    val bundle = Bundle().apply { putSerializable("ImageObject", finalObject) }
                    intent.putExtras(bundle)

                    startActivity(intent)
                }
            }
        }
    }


    fun googleSearch(context: Context,
                     searchString: String,
                     startIndex : String) {
        val request =
            createSearchRequest(searchURL(searchString, startIndex))

        HttpClient.client
            .newCall(request)
            .enqueue(object : Callback {
                @Throws(Exception::class)
                override fun onResponse(response: Response?) {
                    try {
                        if(!response!!.isSuccessful)
                            throw IOException("Unexpected code $response")

                        val json = response.body().string()
                        if(json == null) logError(Error("Response is null"))
                        else {
                            val list = handleSearchResponse(json)
                            list?.let {
                                runOnUiThread {
                                    (recyclerview.adapter as ImageAdapter).apply {
                                        addImages(it)
                                        notifyDataSetChanged()
                                    }
                                }
                            }
                        }

                    } catch (e : IOException) {
                        logError(Error("Error occured while handling response. ${e.printStackTrace()}"))
                    }
                }

                override fun onFailure(request: Request?, e: IOException?) {
                    toast(context, "Failed to preocess your search!! Please try again later.")
                }
            })

    }
}

