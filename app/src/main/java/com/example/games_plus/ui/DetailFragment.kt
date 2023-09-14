package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.games_plus.R
import com.example.games_plus.databinding.FragmentDetailBinding
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class DetailFragment : Fragment() {


    private lateinit var binding: FragmentDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNav?.visibility = View.GONE


        viewModel.favoriteGames.observe(viewLifecycleOwner) {
            thumbButtons()
        }


        viewModel.currentResult.observe(viewLifecycleOwner) {


            binding.tvGameTitle.text = it.name

            binding.tvDescription.text = Html.fromHtml(it.description?.trim() ?: "")
            binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()


            val youtubeId = viewModel.assignYouTubeIdsToGame(it).youtubeId.firstOrNull()
            if (youtubeId != null) {
                binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(youtubeId, 0f)
                        youTubePlayer.pause()
                        binding.youtubePlayerView.setOnClickListener {
                            youTubePlayer.play()
                        }
                    }
                })
            }

        }





    }













    private fun thumbButtons() {
        val selectedGame = viewModel.currentResult.value


        val isFavored = selectedGame?.let { viewModel.isGameFavored(it.id) } ?: false


        if (isFavored) {
            binding.btnAddToFavorites.setImageResource(R.drawable.baseline_bookmark_24_green)
        } else {
            binding.btnAddToFavorites.setImageResource(R.drawable.baseline_bookmark_border_24_black)
        }

        binding.btnAddToFavorites.setOnClickListener {
            if (selectedGame != null) {
                if (isFavored) {
                    binding.btnAddToFavorites.setImageResource(R.drawable.baseline_bookmark_border_24_black)
                    viewModel.removeFromFavorites(selectedGame)
                } else {
                    binding.btnAddToFavorites.setImageResource(R.drawable.baseline_bookmark_24_green)
                    viewModel.addToFavorites(selectedGame)
                }
            }
        }
    }







}









/*val imageUrl = it.image?.medium_url
binding.playGameTitleCover.load(imageUrl)*/





/*val webView = binding.playGameTitle
webView.settings.javaScriptEnabled = true

val videoId = "c0i88t0Kacs"
webView.loadUrl("https://www.youtube.com/embed/$videoId")

binding.playGameTitleCover.setOnClickListener {

    it.visibility = View.GONE

    webView.loadUrl("https://www.youtube.com/embed/$videoId?autoplay=1")
}*/



/*binding.tvDescription.text = Html.fromHtml(it.description).toString().trim()*/

/*if (it.description != null) {
    binding.tvDescription.text = Html.fromHtml(it.description).toString().trim()
} else {
    binding.tvDescription.text = ""
}*/