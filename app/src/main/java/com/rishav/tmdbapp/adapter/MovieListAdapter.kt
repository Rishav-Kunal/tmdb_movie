package com.rishav.tmdbapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rishav.tmdbapp.R
import com.rishav.tmdbapp.data.Movie
import com.rishav.tmdbapp.databinding.MovieListItemBinding
import com.rishav.tmdbapp.util.ItemClickListener
import javax.inject.Inject


class MovieListAdapter @Inject constructor() : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var data: List<Movie> = ArrayList()
    var clickListener : ItemClickListener<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieListItemBinding: MovieListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_list_item, parent, false
        )
        return MovieViewHolder(movieListItemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(data[position])

    fun updateData(newList: List<Movie>) {
        val oldList = data
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(MovieDiffUtil(oldList, newList))
        data = newList
        diffResult.dispatchUpdatesTo(this)
    }


    inner class MovieViewHolder(private var movieListItemBinding: MovieListItemBinding) : RecyclerView.ViewHolder(movieListItemBinding.root) {
        fun bind(movie: Movie) = with(movieListItemBinding) {
            this.root.setOnClickListener { clickListener?.onItemClicked(movie) }
            this.movie = movie
        }
    }

    inner class MovieDiffUtil(var oldList: List<Movie>, var newList: List<Movie>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}