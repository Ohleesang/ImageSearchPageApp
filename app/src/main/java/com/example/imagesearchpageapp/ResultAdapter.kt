package com.example.imagesearchpageapp


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imagesearchpageapp.databinding.RecyclerViewItemBinding


class ResultAdapter(private val context: Context,private var mItems: MutableList<Item>) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {


    inner class ViewHolder(binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbNailImage = binding.ivThumbNail
        val siteName = binding.tvSiteName
        val dateTime = binding.tvDateTime
        val likeAnimation = binding.lavHeart
        val cardView = binding.cvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]

        holder.apply {

            //Url 이미지 추가
            Glide.with(context)
                .load(item.thumbNailUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(thumbNailImage)

            siteName.text = item.siteName
            dateTime.text = item.dateTime
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

        holder.cardView.setOnClickListener {
            // 좋아요 애니메이션 토클 형식
            holder.likeAnimation.apply {
                speed =
                    if (item.isLike) -6f else 2f
                if (item.isLike) setMinAndMaxProgress(0f, 0.5f)
                else setMinAndMaxProgress(0f, 1f)
                playAnimation()
            }
            item.isLike = !item.isLike
            updateData(item)
        }
    }


    override fun getItemCount() = mItems.size


    /**
     * 데이터가 업데이트 될때 내 보관함에서 삭제되면 다시 그림
     */
    private fun updateData(item: Item) {
        val idx : Int

        if (item.isLike) ListItem.addLikeItems(item)
        else {
            idx = ListItem.deleteLikeItems(item)
            if (mItems == ListItem.likeItems)
                notifyItemRemoved(idx)
        }
    }

    fun updateUI() {
        mItems = ListItem.mItems
        notifyDataSetChanged()
    }



}