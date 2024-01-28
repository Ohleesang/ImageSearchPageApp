package com.example.imagesearchpageapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imagesearchpageapp.databinding.RecyclerViewItemBinding

class ResultAdapter : ListAdapter<Item, ResultAdapter.ResultViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) =
                oldItem.document == newItem.document

            override fun areContentsTheSame(oldItem: Item, newItem: Item) =
                oldItem == newItem
        }
    }

    private lateinit var context: Context

    inner class ResultViewHolder(binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbNailImage = binding.ivThumbNail
        val siteName = binding.tvSiteName
        val dateTime = binding.tvDateTime
//        val likeAnimation = binding.lavHeart
//        val cardView = binding.cvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        context = parent.context
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {

            //URL 이미지 설정
            Glide.with(context)
                .load(item.document.thumbNailUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(thumbNailImage)

            siteName.text = item.document.siteName
            dateTime.text = item.document.dateTime
        }
    }
}