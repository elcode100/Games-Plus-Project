package com.example.games_plus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.R
import com.example.games_plus.data.model.Game
import com.example.games_plus.databinding.MobileGamesTitlesItemBinding
import com.example.games_plus.ui.viewmodels.MainViewModel

class MobileGamesAdapter(var dataset3: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<MobileGamesAdapter.MobileGamesViewHolder>() {

    inner class MobileGamesViewHolder(val binding: MobileGamesTitlesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MobileGamesAdapter.MobileGamesViewHolder {
        val binding = MobileGamesTitlesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MobileGamesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset3.size
    }

    override fun onBindViewHolder(holder: MobileGamesViewHolder, position: Int) {
        val item = dataset3[position]


            val imageUrl = item.image?.medium_url

            holder.binding.apply {

                Glide.with(ivGameTitleImage).load(imageUrl).into(ivGameTitleImage)

            }


            holder.binding.ivGameTitleImage.setOnClickListener {
                viewModel.updateResult(item)
                val navController = holder.itemView.findNavController()
                navController.navigate(R.id.detailFragment)



            }










    }
}
