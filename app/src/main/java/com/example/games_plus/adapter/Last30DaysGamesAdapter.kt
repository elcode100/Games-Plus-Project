package com.example.games_plus.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.data.models.games.Game
import com.example.games_plus.databinding.GameTitlesItem2Binding
import com.example.games_plus.databinding.Last30DaysItemBinding
import com.example.games_plus.ui.HomeFragmentDirections
import com.example.games_plus.ui.viewmodels.MainViewModel

class Last30DaysGamesAdapter(var dataset: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<Last30DaysGamesAdapter.Last30DaysGamesViewHolder>() {

    inner class Last30DaysGamesViewHolder(val binding: Last30DaysItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Last30DaysGamesAdapter.Last30DaysGamesViewHolder {
        val binding = Last30DaysItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Last30DaysGamesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Last30DaysGamesViewHolder, position: Int) {

        val item = dataset[position]

        val imageUrl = item.image?.mediumUrl
        val expectedYear = item.expectedReleaseYear
        val expectedMonth = item.expectedReleaseMonth
        val expectedDay = item.expectedReleaseDay

        val releaseDate = item.originalReleaseDate

        holder.binding.apply {

            Glide.with(ivGameTitleImage).load(imageUrl).into(ivGameTitleImage)
            tvGameTitle.text = item.name
            tvReleaseDate.text = formatDate(releaseDate, expectedYear, expectedMonth, expectedDay)

        }





        holder.binding.cardGameTitle.setOnClickListener {
            try {
                it.isClickable = false
                viewModel.updateResult(item)
                viewModel.loadGenresForSelectedGame(item)
                viewModel.loadDevelopersForSelectedGame(item)

                val navController = holder.itemView.findNavController()
                navController.navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment())

                it.postDelayed({ it.isClickable = true }, 500)
            } catch (e: Exception) {

                Log.e("ERROR LAST 30 DAYS ADAPTER", "Error on clicking: ${e.message}")
            }
        }



    }

    private fun formatDate(releaseDate: String?, expectedYear: Int?, expectedMonth: Int?, expectedDay: Int?): String {
        return when {
            releaseDate != null -> releaseDate
            expectedYear != null && expectedMonth != null && expectedDay != null -> "$expectedYear-${
                expectedMonth.toString().padStart(2, '0')
            }-${expectedDay.toString().padStart(2, '0')}"

            expectedYear != null && expectedMonth != null -> "$expectedYear-${
                expectedMonth.toString().padStart(2, '0')
            }"

            expectedYear != null -> "$expectedYear"
            else -> "-"
        }
    }
}