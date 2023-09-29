package com.example.games_plus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.games_plus.R
import com.example.games_plus.data.model.Game
import com.example.games_plus.databinding.LibraryItemBinding
import com.example.games_plus.ui.viewmodels.MainViewModel

class LibraryAdapter(private val context: Context, var dataset: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {


    inner class LibraryViewHolder(val binding: LibraryItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryAdapter.LibraryViewHolder {
        val binding = LibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: LibraryAdapter.LibraryViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.tvGameTitleLibrary.text = item.name
        val imageUrl = item.image?.medium_url
        holder.binding.imgGameLibrary.load(imageUrl)

        holder.binding.cardLibrary.setOnClickListener {

            viewModel.updateResult(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.detailFragment)


        }




    }
}