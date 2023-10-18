package com.example.games_plus.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.data.models.News
import com.example.games_plus.data.models.news.Article
import com.example.games_plus.databinding.NewsItemBinding
import com.example.games_plus.ui.HomeFragmentDirections
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


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = dataset[position]

        val imageUrl = item.image?.original

        holder.binding.apply {

            Glide.with(ivNewsHeadlineImage).load(imageUrl).into(ivNewsHeadlineImage)
            tvArticleTitle.text = item.title
            tvPublishDate.text = item.publishDate
            tvPublishesBy.text = "By ${item.authors}"


        }


        holder.binding.articleCard.setOnClickListener {

            try {
                it.isClickable = false
                viewModel.updateNewsData(item)


                val navController = holder.itemView.findNavController()
                navController.navigate(HomeFragmentDirections.actionNavigationHomeToNewsDetailFragment())

                it.postDelayed({ it.isClickable = true }, 500)
            } catch (e: Exception) {

                Log.e("ERROR NEWS ADAPTER", "Error on clicking: ${e.message}")
            }

        }


    }



}