package com.example.games_plus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.data.models.News
import com.example.games_plus.data.models.news.Article
import com.example.games_plus.databinding.NewsItemBinding
import com.example.games_plus.ui.viewmodels.MainViewModel


class NewsAdapter(var dataset: List<Article>, private val viewModel: MainViewModel) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = dataset[position]

        val imageUrl = item.image.original

        holder.binding.apply {

            Glide.with(ivNewsHeadlineImage).load(imageUrl).into(ivNewsHeadlineImage)
           /* tvArticleTitle.text = item.title*/


        }



    }
}