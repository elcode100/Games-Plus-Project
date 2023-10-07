package com.example.games_plus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.games_plus.R
import com.example.games_plus.data.model.Game
import com.example.games_plus.databinding.SearchResultItemBinding
import com.example.games_plus.ui.viewmodels.MainViewModel

class SearchAdapter(private val context: Context, var dataset: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {


    inner class SearchViewHolder(val binding: SearchResultItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchViewHolder {
        val binding = SearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val item = dataset[position]

        val imageUrl = item.image?.medium_url

        holder.binding.ivSearchGameCover.load(imageUrl)
        holder.binding.ivSearchGameTitle.text = item.name

        holder.binding.cardSearchResult.setOnClickListener {

            viewModel.updateResult(item)
            viewModel.loadGenresForSelectedGame(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.detailFragment)


        }
    }



}