package com.example.dunzoassignment

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dunzoassignment.model.FinalObject

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val image = intent.extras?.getSerializable("ImageObject") as FinalObject
        logDebug(image.toString())
    }

}