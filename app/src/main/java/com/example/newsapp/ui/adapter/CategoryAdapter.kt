package com.example.newsapp.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.databinding.CategoryDetailItemBinding
import com.example.newsapp.databinding.CenterImageNewsItemBinding

class CategoryAdapter(private val categoryNews: List<ArticlesItem>)  :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(var binding: CategoryDetailItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val binding = CategoryDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = categoryNews.size

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        with(holder.binding){
            Glide.with(holder.itemView.context).load(categoryNews[position].urlToImage).error(R.drawable.ic_launcher_background).into(ivCategoryImage)
            tvCategoryTitle.text = categoryNews[position].title
            tvCategorySource.text = categoryNews[position].source.name
            tvCategoryDate.text = categoryNews[position].publishedAt.substring(0,10)
            clCategoryItem.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(categoryNews[position].url)
                it.context.startActivity(intent)
            }
        }
    }
}