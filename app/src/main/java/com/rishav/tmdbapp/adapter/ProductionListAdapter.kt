package com.rishav.tmdbapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rishav.tmdbapp.R
import com.rishav.tmdbapp.data.Movie
import com.rishav.tmdbapp.data.ProductionCompany
import com.rishav.tmdbapp.databinding.MovieListItemBinding
import com.rishav.tmdbapp.databinding.ProductionListItemBinding
import com.rishav.tmdbapp.util.ItemClickListener
import javax.inject.Inject


class ProductionListAdapter @Inject constructor() : RecyclerView.Adapter<ProductionListAdapter.ProductionViewHolder>() {

    private var data: List<ProductionCompany> = ArrayList()
    var clickListener : ItemClickListener<ProductionCompany>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionViewHolder {
        val listItemBinding: ProductionListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.production_list_item, parent, false
        )
        return ProductionViewHolder(listItemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductionViewHolder, position: Int) =
            holder.bind(data[position])

    fun updateData(newList: List<ProductionCompany>) {
        val oldList = data
        val diffResult: DiffUtil.DiffResult =
                DiffUtil.calculateDiff(MovieDiffUtil(oldList, newList))
        data = newList
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ProductionViewHolder(private var listItemBinding: ProductionListItemBinding) : RecyclerView.ViewHolder(listItemBinding.root) {
        fun bind(productionCompany: ProductionCompany) = with(listItemBinding) {
            this.root.setOnClickListener { clickListener?.onItemClicked(productionCompany) }
            this.production = productionCompany
        }
    }

    inner class MovieDiffUtil(var oldList: List<ProductionCompany>, var newList: List<ProductionCompany>) :
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