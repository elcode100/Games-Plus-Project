package com.example.games_plus.adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.example.games_plus.data.models.games.Game
import com.example.games_plus.databinding.GameTitlesItemBinding
import com.example.games_plus.ui.HomeFragmentDirections


class HomeAdapter(var dataset: List<Game>, private val viewModel: MainViewModel) : RecyclerView.Adapter<HomeAdapter.GameTitlesViewHolder>() {

    inner class GameTitlesViewHolder(val binding: GameTitlesItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.GameTitlesViewHolder {
        val binding = GameTitlesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameTitlesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GameTitlesViewHolder, position: Int) {
        val item = dataset[position]

        val imageUrl = item.image?.mediumUrl
        val userScore = item.averageUserScore.toString()

        holder.binding.apply {

            Glide.with(ivGameTitleImage).load(imageUrl).into(ivGameTitleImage)
            tvGameTitle.text = item.name
            tvReleaseDate.text = item.originalReleaseDate


            if (item.averageUserScore == null) {
                tvReviewScore.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            } else {
                tvReviewScore.visibility = View.VISIBLE
                tvReviewScore.text = " / ${item.averageUserScore}"
                progressBar.visibility = View.GONE
            }




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

                Log.e("ERROR HOME ADAPTER", "Error on clicking: ${e.message}")
            }
        }






    }
}









/*holder.binding.ivGameTitleImage.setOnClickListener {
    viewModel.updateResult(item)
    viewModel.loadGenresForSelectedGame(item)

    val navController = holder.itemView.findNavController()
    navController.navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment())


}*/







/*holder.binding.ivGameTitleImage.setOnClickListener {
            clickListener.onGameTitleClick(item)

        }*/



/*holder.binding.ivGameTitleImage.setOnClickListener {
            viewModel.updateResult(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.detailFragment)





        }*/







/*private val clickListener: HomeAdapterClickListener*/

/*tvGameTitle.text = item.name*/










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