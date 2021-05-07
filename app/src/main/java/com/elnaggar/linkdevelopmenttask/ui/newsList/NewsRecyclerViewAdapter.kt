package com.elnaggar.linkdevelopmenttask.ui.newsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.elnaggar.linkdevelopmenttask.databinding.NewsItemBinding

class NewsRecyclerViewAdapter(
    private val values: List<UiNews>,
    private val onItemClickListener: (UiNews)->Unit
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item,onItemClickListener)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UiNews, onItemClickListener: (UiNews) -> Unit){
            binding.authorTextView.text=item.by
            binding.dateTextView.text=item.date
            binding.titleImageView.text=item.title
            binding.imageView.load(item.imageUrl)
            onItemClickListener.invoke(item)
        }

    }

}