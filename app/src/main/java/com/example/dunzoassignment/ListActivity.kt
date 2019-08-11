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

class ListActivity : AppCompatActivity() {

    private val searchTag = "Search"
    lateinit var searchText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        searchText = intent.getStringExtra(searchTag)!!

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()

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

