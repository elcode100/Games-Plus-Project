package com.example.games_plus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.games_plus.R
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.example.games_plus.data.model.Game
import com.example.games_plus.databinding.GameTitlesItemBinding

class HomeAdapter(private val context: Context, var dataset: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<HomeAdapter.GameTitlesViewHolder>() {

    inner class GameTitlesViewHolder(val binding: GameTitlesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.GameTitlesViewHolder {
        val binding = GameTitlesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameTitlesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: GameTitlesViewHolder, position: Int) {
        val item = dataset[position]

        val imageUrl = item.image?.medium_url

        holder.binding.apply {

            Glide.with(ivGameTitleImage).load(imageUrl).into(ivGameTitleImage)
            tvGameTitle.text = item.name
        }


        holder.binding.ivGameTitleImage.setOnClickListener {
            viewModel.updateResult(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.detailFragment)





        }



    }
}















/*override fun getItemCount(): Int {
    return if (dataset.isNotEmpty()) Integer.MAX_VALUE else 0
}

override fun onBindViewHolder(holder: HomeAdapter.GameTitlesViewHolder, position: Int) {
    val item = dataset[position % dataset.size]

    val imageUrl = item.image?.medium_url

    holder.binding.apply {

        Glide.with(ivGameTitleImage).load(imageUrl).into(ivGameTitleImage)
        tvGameTitle.text = item.name
    }


    holder.binding.ivGameTitleImage.setOnClickListener {
        viewModel.updateResult(item)
        val navController = holder.itemView.findNavController()
        navController.navigate(R.id.detailFragment)



    }



}*/












/*Glide.with(ivGameTitleImage)
.load(imageUrl)
.diskCacheStrategy(DiskCacheStrategy.ALL)
.into(ivGameTitleImage)*/


/*holder.binding.tvGameTitle.text = item.name*/

/*holder.binding.ivGameTitleImage.load(imageUrl)*/

/*holder.binding.cardGameTitle.setOnClickListener {
        viewModel.updateResult(item)
        val navController = holder.itemView.findNavController()
        navController.navigate(R.id.detailFragment)
        /*Toast.makeText(holder.binding.root.context, item.name, Toast. LENGTH_SHORT) . show()*/
    }*/