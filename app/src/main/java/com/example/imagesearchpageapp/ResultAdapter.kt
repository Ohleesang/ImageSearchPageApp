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
            if(item.isLike){
                likeAnimation.setMinAndMaxProgress(1f,1f)
                likeAnimation.playAnimation()
            }
        }

        holder.cardView.setOnClickListener {
            holder.likeAnimation.apply {
                speed =
                    if (item.isLike) -6f else 1f
                if(item.isLike) setMinAndMaxProgress(0f,0.5f)
                else setMinAndMaxProgress(0f,1f)
                playAnimation()
            }
            item.isLike = !item.isLike
        }
    }


    override fun getItemCount() = mItems.size


}