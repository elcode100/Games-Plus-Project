package com.example.games_plus.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.data.models.news.Article
import com.example.games_plus.databinding.NewsFragmentItemBinding
import com.example.games_plus.ui.HomeFragmentDirections
import com.example.games_plus.ui.NewsFragmentDirections
import com.example.games_plus.ui.viewmodels.MainViewModel

class NewsFragmentAdapter(var dataset: List<Article>, private val viewModel: MainViewModel) : RecyclerView.Adapter<NewsFragmentAdapter.NewsFragmentViewHolder>() {

    inner class NewsFragmentViewHolder(val binding: NewsFragmentItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFragmentAdapter.NewsFragmentViewHolder {
        val binding = NewsFragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsFragmentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsFragmentViewHolder, position: Int) {
        val item = dataset[position]

        val imageUrl = item.image?.original

        holder.binding.apply {

            Glide.with(ivArticleImage).load(imageUrl).into(ivArticleImage)
            tvArticleTitle.text = item.title
            tvPublishDate.text = item.publishDate
            tvPublishesBy.text = "By ${item.authors}"



        }



        holder.binding.newsCard.setOnClickListener {

            try {
                it.isClickable = false
                viewModel.updateNewsData(item)


                val navController = holder.itemView.findNavController()
                navController.navigate(NewsFragmentDirections.actionNavigationNewsToNewsDetailFragment())

                it.postDelayed({ it.isClickable = true }, 500)
            } catch (e: Exception) {

                Log.e("ERROR NEWS FRAGMENT ADAPTER", "Error on clicking: ${e.message}")
            }

        }



    }
}