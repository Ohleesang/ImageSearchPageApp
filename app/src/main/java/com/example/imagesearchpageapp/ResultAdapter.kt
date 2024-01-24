package com.example.imagesearchpageapp


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchpageapp.databinding.RecyclerViewItemBinding


class ResultAdapter(private val mItems: MutableList<Item>) :
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
            thumbNailImage.setImageResource(item.thumbNaileUri)
            siteName.text = item.siteName
            dateTime.text = item.dateTime
            if (item.isLike) {
                // 돌아올 때 좋아요 버튼 사진 유지
                likeAnimation.setMinAndMaxProgress(1f, 1f)
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
            updateData(item, position)
        }
    }


    override fun getItemCount() = mItems.size


    /**
     * 데이터가 업데이트 될때 내 보관함에서 삭제되면 다시 그림
     */
    private fun updateData(item: Item, position: Int) {
        if (item.isLike) List.addLikeItems(item)
        else {
            List.deleteLikeItems(item)
            if (mItems == List.likeItems)
                notifyItemRemoved(position)
        }
    }


}