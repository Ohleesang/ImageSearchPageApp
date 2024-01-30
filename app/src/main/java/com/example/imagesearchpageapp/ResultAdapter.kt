package com.example.imagesearchpageapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imagesearchpageapp.data.Item
import com.example.imagesearchpageapp.databinding.RecyclerViewItemBinding

interface OnClickItem {
    fun onClick(item: Item)

}

class ResultAdapter : ListAdapter<Item, ResultAdapter.ResultViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) =
                oldItem.itemDocument == newItem.itemDocument

            override fun areContentsTheSame(oldItem: Item, newItem: Item) =
                oldItem == newItem
        }
    }

    private lateinit var context: Context
    private var onClickItem: OnClickItem? = null

    inner class ResultViewHolder(binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbNailImage = binding.ivThumbNail
        val siteName = binding.tvSiteName
        val dateTime = binding.tvDateTime
        val likeAnimation = binding.lavHeart
        val cardView = binding.cvItem

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
                .load(item.itemDocument.thumbNailUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(thumbNailImage)

            siteName.text = item.itemDocument.title
            dateTime.text = item.itemDocument.dateTime

            /**
             *  Like 애니메이션
             */
            //클릭시 애니메이션
            cardView.setOnClickListener {
                onClickItem?.onClick(item)
                //item 상태에 따라 좋아요 표시/해제
                likeAnimation.apply {
                    speed =
                        if (item.isLike) 2f else -6f
                    if (item.isLike) setMinAndMaxProgress(0f, 1f)
                    else setMinAndMaxProgress(0f, 0.5f)
                    playAnimation()
                }
            }

            //다시 그릴 때 애니메이션
            if (item.isLike) {
                // 돌아올 때 좋아요 버튼 사진 유지
                likeAnimation.setMinAndMaxProgress(0.5f, 1f)
                likeAnimation.playAnimation()
            }
            else{
                likeAnimation.setMinAndMaxProgress(0f, 0f)
                likeAnimation.playAnimation()
            }


        }
    }

    fun setOnClickedItem(listener: OnClickItem) {
        onClickItem = listener
    }
}