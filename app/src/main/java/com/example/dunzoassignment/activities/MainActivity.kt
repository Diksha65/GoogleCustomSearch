package com.example.dunzoassignment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.dunzoassignment.R
import com.example.dunzoassignment.extensions.logDebug
import com.example.dunzoassignment.extensions.onChange
import com.example.dunzoassignment.extensions.toast
import kotlinx.android.synthetic.main.activity_main.*

//API had only 100 calls per day hence it stuck me for a long time.
//The response was not consistent. The data was very bad
//the searchType image was not enabled in the api so it was getting difficult to fetch images
//images were out of sync with the search topic

class MainActivity : AppCompatActivity() {

    var searchQuery : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logDebug("onCreate")

        searchText.onChange {text -> searchQuery = text}

        searchButton.setOnClickListener {

            if(searchQuery.isEmpty()) {
                toast(this, "Please enter a valid search text to proceed.")
            } else {
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("Search", searchQuery)
                startActivity(intent)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        searchText.setText("")
        searchQuery = ""
    }
}
