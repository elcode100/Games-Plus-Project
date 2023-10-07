package com.example.games_plus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.R
import com.example.games_plus.data.model.Game
import com.example.games_plus.databinding.GameTitlesItem2Binding
import com.example.games_plus.ui.HomeFragmentDirections
import com.example.games_plus.ui.viewmodels.MainViewModel

class HomeAdapter2(var dataset2: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<HomeAdapter2.HomeAdapter2ViewHolder>() {

    inner class HomeAdapter2ViewHolder(val binding: GameTitlesItem2Binding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter2.HomeAdapter2ViewHolder {
        val binding = GameTitlesItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeAdapter2ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset2.size
    }

    override fun onBindViewHolder(holder: HomeAdapter2ViewHolder, position: Int) {

            val item = dataset2[position]

            val imageUrl = item.image?.medium_url

            holder.binding.apply {

                Glide.with(ivGameTitleImage).load(imageUrl).into(ivGameTitleImage)
                /*tvGameTitle.text = item.name*/
            }


            /*holder.binding.ivGameTitleImage.setOnClickListener {
                viewModel.updateResult(item)
                val navController = holder.itemView.findNavController()
                navController.navigate(R.id.detailFragment)



            }*/


        holder.binding.ivGameTitleImage.setOnClickListener {
            viewModel.updateResult(item)
            viewModel.loadGenresForSelectedGame(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment())





        }
            
            
            
            


        



    }
}




/*
class HomeAdapter2(var dataset2: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<HomeAdapter2.HomeAdapter2ViewHolder>() {

    inner class HomeAdapter2ViewHolder(val binding: GameTitlesItem2Binding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter2.HomeAdapter2ViewHolder {
        val binding = GameTitlesItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeAdapter2ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (dataset2.isNotEmpty()) Integer.MAX_VALUE else 0
    }

    override fun onBindViewHolder(holder: HomeAdapter2ViewHolder, position: Int) {
        if (dataset2.isNotEmpty()) {
            val item = dataset2[position % dataset2.size]

            val imageUrl = item.image?.medium_url

            holder.binding.apply {

                Glide.with(ivGameTitleImage).load(imageUrl).into(ivGameTitleImage)
                */
/*tvGameTitle.text = item.name*//*

            }


            holder.binding.ivGameTitleImage.setOnClickListener {
                viewModel.updateResult(item)
                val navController = holder.itemView.findNavController()
                navController.navigate(R.id.detailFragment)



            }




        }





    }
}*/
