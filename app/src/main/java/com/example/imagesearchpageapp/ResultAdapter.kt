package com.example.imagesearchpageapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchpageapp.databinding.RecyclerViewItemBinding

class ResultAdapter(private val mItems : MutableList<Item>): RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    inner class ViewHolder(binding : RecyclerViewItemBinding) :RecyclerView.ViewHolder(binding.root){
        val thumbNailImage = binding.ivThumbNail
        val siteName = binding.tvSiteName
        val dateTime = binding.tvDateTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]
        holder.apply{
            thumbNailImage.setImageResource(item.thumbNaileUri)
            siteName.text = item.siteName
            dateTime.text = item.dateTime
        }
    }

    override fun getItemCount() = mItems.size


}