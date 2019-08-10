package com.example.dunzoassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dunzoassignment.searchapi.googleSearch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var searchQuery : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText.onChange {text -> searchQuery = text}

        searchButton.setOnClickListener {
            googleSearch(this, searchQuery, "1") {

            }
        }
    }
}
