package com.example.dunzoassignment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.dunzoassignment.model.FinalObject
import kotlinx.android.synthetic.main.activity_detail.*
import android.graphics.BitmapFactory

class DetailActivity : AppCompatActivity() {

    var image : FinalObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        image = intent.extras?.getSerializable("ImageObject") as FinalObject

        if(image == null) {
            toast(this, "Error while fetching the details of the image")
        } else {
            setUpViews(image)
        }
    }

    fun setUpViews(image : FinalObject?) {
        Glide.with(this)
            .load(image!!.src)
            .centerCrop()
            .placeholder(R.drawable.ic_placeeholder)
            .into(imageView)

        imageTitle.text = image.title
        imageUrl.text = image.link
        imageDescription.text = image.description

        imageUrl.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(this@DetailActivity, R.color.colorPrimary))
                .addDefaultShareMenuItem()
                .setShowTitle(true)
                .setCloseButtonIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_close))
                .setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
                .build()

            builder.launchUrl(this@DetailActivity, Uri.parse(image.link.toString()))
        }
    }

}