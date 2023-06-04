package com.example.newsapp.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.databinding.CenterImageNewsItemBinding

class HeadlineAdapter(private val headlinesNews: List<ArticlesItem>) :
    RecyclerView.Adapter<HeadlineAdapter.ViewHolder>() {

    class ViewHolder(var binding: CenterImageNewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CenterImageNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = headlinesNews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            Glide.with(holder.itemView.context).load(headlinesNews[position].urlToImage).error(R.drawable.ic_launcher_background).into(ivHeadlineImage)
            tvHeadlineTitle.text =headlinesNews[position].title
            tvHeadlineSource.text =headlinesNews[position].source.name
            tvHeadlineDate.text =headlinesNews[position].publishedAt.substring(0,10)
            llHeadlineItem.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(headlinesNews[position].url)
                it.context.startActivity(intent)
            }
        }
    }
}