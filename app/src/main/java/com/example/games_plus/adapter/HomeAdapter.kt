package com.example.games_plus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.games_plus.R
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.example.games_plus.data.model.Result
import com.example.games_plus.databinding.GameTitlesItemBinding

class HomeAdapter(private val context: Context, private var dataset: List<Result>, private val viewModel: MainViewModel) : RecyclerView.Adapter<HomeAdapter.GameTitlesViewHolder>() {



    inner class GameTitlesViewHolder(val binding: GameTitlesItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.GameTitlesViewHolder {

        val binding = GameTitlesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameTitlesViewHolder(binding)
    }




    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: GameTitlesViewHolder, position: Int) {

        val item = dataset[position]

        holder.binding.tvGameTitle.text = item.name
        holder.binding.ivGameTitleImage.load(item.backgroundImage)



        holder.binding.cardGameTitle.setOnClickListener {

            viewModel.updateResult(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.detailFragment)
        }
    }



}