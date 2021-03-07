package com.github.daniilbug.beautifuldog.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.daniilbug.beautifuldog.android.R

class LikedDogsAdapter(private val onClick: (photo: View, url: String) -> Unit) :
    ListAdapter<String, LikedDogsAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dog, parent, false)
        return ViewHolder(view).apply {
            itemView.setOnClickListener { onClick(photo, getItem(adapterPosition)) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: AppCompatImageView = itemView.findViewById(R.id.itemDogPhoto)

        fun bind(position: Int) {
            val photoUrl = getItem(position)
            photo.transitionName = photoUrl
            loadImage(photoUrl)
        }

        private fun loadImage(photoUrl: String) {
            Glide.with(itemView)
                .load(photoUrl)
                .transform(RoundedCorners(4))
                .transition(withCrossFade())
                .into(photo)
        }
    }
}