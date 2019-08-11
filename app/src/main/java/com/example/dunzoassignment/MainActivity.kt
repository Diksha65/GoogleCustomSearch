package com.example.dunzoassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var searchQuery : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText.onChange {text -> searchQuery = text}

        searchButton.setOnClickListener {

            if(searchQuery.isEmpty()) {
                toast(this, "Please enter a valid search text to proceed.")
            } else {
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("Search", searchQuery)
                intent.putExtra("startIndex", "1")
                startActivity(intent)
            }
        }
    }
}
