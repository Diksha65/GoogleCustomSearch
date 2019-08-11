package com.example.dunzoassignment

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dunzoassignment.model.FinalObject
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_list.view.itemThumbnailImage

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    private val list = ArrayList<FinalObject>()
    var detailButtonCallback : ((FinalObject) -> Unit)? = null

    fun addDetailButtonCallback(detailButtonCallback : (FinalObject) -> Unit) {
        this.detailButtonCallback = detailButtonCallback
    }

    fun addImages(tempList : ArrayList<FinalObject>) {
        list.addAll(tempList)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val obj = list[position]
        holder.bindObject(obj)

        holder.itemView.setOnClickListener {
            val expanded = obj.isExpanded
            obj.isExpanded = !expanded
            notifyItemChanged(position)
        }

        holder.itemView.detailsButton.setOnClickListener {
            detailButtonCallback?.invoke(obj)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageHolder(inflater.inflate(R.layout.item_list, parent, false))
    }


    class ImageHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindObject(obj : FinalObject) {
            Glide.with(itemView)
                .load(obj.thumbnailsrc)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_placeeholder)
                .into(itemView.itemThumbnailImage)
                .waitForLayout()

            val expanded = obj.isExpanded

            itemView.apply {
                itemThumbnailTitle.visibility = (if (expanded) View.VISIBLE else GONE)
                detailsButton.visibility = (if (expanded) View.VISIBLE else GONE)
                itemThumbnailTitle.text = obj.title
            }
        }
    }
}