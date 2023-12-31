package com.example.games_plus.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.data.models.games.Game
import com.example.games_plus.databinding.MobileGamesTitlesItemBinding
import com.example.games_plus.ui.HomeFragmentDirections
import com.example.games_plus.ui.viewmodels.MainViewModel

class MobileGamesAdapter(var dataset: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<MobileGamesAdapter.MobileGamesViewHolder>() {

    inner class MobileGamesViewHolder(val binding: MobileGamesTitlesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MobileGamesAdapter.MobileGamesViewHolder {
        val binding = MobileGamesTitlesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MobileGamesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MobileGamesViewHolder, position: Int) {
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





        holder.binding.ivGameTitleImage.setOnClickListener {
            try {
                it.isClickable = false
                viewModel.updateResult(item)
                viewModel.loadGenresForSelectedGame(item)
                viewModel.loadDevelopersForSelectedGame(item)

                val navController = holder.itemView.findNavController()
                navController.navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment())

                it.postDelayed({ it.isClickable = true }, 500)
            } catch (e: Exception) {

                Log.e("ERROR MOBILE GAMES ADAPTER", "Error on clicking: ${e.message}")
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




/*
holder.binding.ivGameTitleImage.setOnClickListener {
    viewModel.updateResult(item)
    val navController = holder.itemView.findNavController()
    navController.navigate(R.id.detailFragment)


}*/
