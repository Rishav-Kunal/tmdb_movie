package com.rishav.tmdbapp.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rishav.tmdbapp.R

class BindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            Glide.with(view.context)
                    .load(url)
                    .placeholder(R.drawable.loading_animation)
                    .into(view)
        }


        @JvmStatic
        @BindingAdapter("posterUrl")
        fun loadPosterImage(view: ImageView, url: String?) {
            Glide.with(view.context)
                    .load(url)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_video_camera)
                    .into(view)
        }


        @JvmStatic
        @BindingAdapter("app:isVisible")
        fun setVisibility(view: View, visibility : Boolean) {
            view.visibility = if (visibility) View.VISIBLE else View.GONE
        }
    }
}