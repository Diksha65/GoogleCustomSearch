package com.example.dunzoassignment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dunzoassignment.model.FinalObject
import kotlinx.android.synthetic.main.expandable_item_list.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_list.view.itemThumbnailImage

class ImageAdapter : RecyclerView.Adapter<ImageHolder>() {

    private val list = ArrayList<FinalObject>()

    fun addImages(tempList : ArrayList<FinalObject>) {
        list.addAll(tempList)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val obj = list[position]
        holder.bindObject(obj)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageHolder(inflater.inflate(R.layout.expandable_item_list, parent, false))
    }

}

class ImageHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    fun bindObject(obj : FinalObject) {
        Glide.with(itemView)
            .load(obj.thumbnailsrc)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_placeeholder)
            .into(itemView.itemThumbnailImage)
            .waitForLayout()

        itemView.itemThumbnailTitle.text = obj.title
    }

    override fun onClick(view: View?) {
        logDebug("Item clicked")
//        val intent = Intent(, DetailActivity::class.java).apply {
//
//        }
    }
}