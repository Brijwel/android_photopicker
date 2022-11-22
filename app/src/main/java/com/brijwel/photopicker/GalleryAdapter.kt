package com.brijwel.photopicker

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private var images = mutableListOf<Uri>()

    inner class GalleryViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.findViewById<AppCompatImageView>(R.id.remove).setOnClickListener {
                images.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

        fun setImage(uri: Uri) {
            view.findViewById<AppCompatTextView>(R.id.type).text = uri.getType(view.context)
            if (uri.isVideo(view.context)) {
                view.findViewById<AppCompatImageView>(R.id.imageView)
                    .setImageBitmap(uri.getThumbnail(view.context))
            } else {
                view.findViewById<AppCompatImageView>(R.id.imageView).setImageURI(uri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.setImage(images[position])
    }

    override fun getItemCount(): Int = images.size

    fun addImage(uri: Uri) {
        images.add(uri)
        notifyItemInserted(images.lastIndex)
    }

    fun addImages(uri: List<Uri>) {
        val insertStartIndex = images.count()
        images.addAll(uri)
        notifyItemRangeInserted(insertStartIndex, uri.count())
    }
}