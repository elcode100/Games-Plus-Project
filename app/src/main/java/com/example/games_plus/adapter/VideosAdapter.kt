package com.example.games_plus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.R
import com.example.games_plus.data.models.videos.VideoDetail
import com.example.games_plus.databinding.VideoItemBinding
import com.example.games_plus.ui.viewmodels.MainViewModel

class VideosAdapter(var dataset: List<VideoDetail> = listOf(), private val viewModel: MainViewModel) : RecyclerView.Adapter<VideosAdapter.VideosViewHolder>() {

    inner class VideosViewHolder(val binding: VideoItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosAdapter.VideosViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val item = dataset[position]

        val imageUrl = item.image?.screenUrl


        holder.binding.apply {

            Glide.with(ivVideoPreview).load(imageUrl).into(ivVideoPreview)
            tvVideoTitle.text = item.name


        }




        holder.binding.videosCard.setOnClickListener {
            viewModel.updateVideoData(item)

            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.videoDetailFragment)


        }






    }
}




/*holder.binding.tvVideoTitle.text = item.name
        holder.binding.ivVideoPreview.load(imageUrl)*/