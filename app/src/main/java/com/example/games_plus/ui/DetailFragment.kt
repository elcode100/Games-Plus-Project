package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.games_plus.R
import com.example.games_plus.databinding.FragmentDetailBinding
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var currentVideoTime: Float = 0f

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



        binding.btnBackDetailToHome.setOnClickListener {
            try {
                it.isClickable = false
                findNavController().navigateUp()
                it.postDelayed({ it.isClickable = true }, 500)
            } catch (e: Exception) {

                Log.e("NAVIGATION ERROR", "Error on clicking back from detail to home: ${e.message}")
            }
        }


        viewModel.favoriteGames.observe(viewLifecycleOwner) {
            thumbButtons()
        }

        binding.tvDeck.setOnClickListener {


            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToDescriptionFragment())
        }



        binding.tvOpenVideos.setOnClickListener {

            findNavController().navigate(R.id.videosFragment)
            viewModel.loadVideos()
        }

        binding.btnVideos.setOnClickListener {

            findNavController().navigate(R.id.videosFragment)
            viewModel.loadVideos()
        }


        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNav?.visibility = View.GONE

        OverScrollDecoratorHelper.setUpOverScroll(binding.detailScroll)



        viewModel.currentGame.observe(viewLifecycleOwner) {
            binding.tvGameTitle.text = it.name

            val genreNames = it.genres?.joinToString(", ") { genre -> genre.name } ?: "-"
            binding.tvDetailGenre.text = genreNames

            val platformNames = it.platforms?.joinToString(", ") { platform -> platform.abbreviation.toString() } ?: "-"
            binding.tvDetailPlatforms.text = platformNames


            val developerNames = it.developers?.joinToString(", ") { developer -> developer.name } ?: "-"
            binding.tvDetailDeveloper.text = developerNames

            binding.tvDetailGuid.text = it.guid

            binding.tvDeck.text = it.deck

            val imageUrl = it.image?.mediumUrl
            binding.detailGameTitleCover.load(imageUrl)



            if (it.averageUserScore == null) {


                binding.tvScore.text = ""
                binding.tvStar.visibility = View.GONE

            } else {

                binding.tvScore.text = it.averageUserScore.toString()
                binding.tvStar.visibility = View.VISIBLE

            }


            val expectedYear = it.expectedReleaseYear
            val expectedMonth = it.expectedReleaseMonth
            val expectedDay = it.expectedReleaseDay

            val releaseDate = it.originalReleaseDate


            binding.tvDetailRelease.text = formatDate(releaseDate, expectedYear, expectedMonth, expectedDay)









            /*val youtubeIds = viewModel.assignYouTubeIdsToGame(it).youId
            val youtubeId = if (youtubeIds.isNotEmpty()) youtubeIds[0] else null

            if (youtubeId != null) {
                binding.youtubePlayerView.visibility = View.VISIBLE
                binding.detailGameTitleCover.visibility = View.GONE

                binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.cueVideo(youtubeId, currentVideoTime)
                        binding.youtubePlayerView.setOnClickListener {
                            youTubePlayer.play()
                        }
                    }
                })
            } else {
                binding.youtubePlayerView.visibility = View.GONE
                binding.detailGameTitleCover.visibility = View.VISIBLE
                val imageUrl = it.image?.mediumUrl
                binding.detailGameTitleCover.load(imageUrl)
            }*/
        }





    }






   /* private fun thumbButtons() {
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
    }*/


    private fun thumbButtons() {
        val selectedGame = viewModel.currentGame.value


        val isFavored = selectedGame?.let { viewModel.isGameFavored(it.id) } ?: false
        if (isFavored) {
            binding.btnAddToFavorites.setImageResource(R.drawable.baseline_bookmark_24_green)
        } else {
            binding.btnAddToFavorites.setImageResource(R.drawable.baseline_bookmark_border_24_black)
        }

        binding.btnAddToFavorites.setOnClickListener {
            selectedGame?.let { viewModel.toggleFavoriteStatus(it) }


            val updatedIsFavored = selectedGame?.let { viewModel.isGameFavored(it.id) } ?: false
            if (updatedIsFavored) {
                binding.btnAddToFavorites.setImageResource(R.drawable.baseline_bookmark_24_green)
            } else {
                binding.btnAddToFavorites.setImageResource(R.drawable.baseline_bookmark_border_24_black)
            }
        }
    }



    private fun formatDate(
        releaseDate: String?,
        expectedYear: Int?,
        expectedMonth: Int?,
        expectedDay: Int?
    ): String {
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






    /*@RequiresApi(Build.VERSION_CODES.R)
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {


            if (binding.youtubePlayerView.visibility == View.VISIBLE) {

                uiLandscape()

                val params = binding.youtubePlayerView.layoutParams as ConstraintLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                binding.youtubePlayerView.layoutParams = params

                binding.tvGameTitle.visibility = View.GONE
                binding.btnAddToFavorites.visibility = View.GONE
                *//*binding.nestedScrollView.visibility = View.GONE*//*
                binding.innerConstrainLayout.visibility = View.GONE
                binding.detailToolbar.visibility = View.GONE
                binding.tvDetailGenre.visibility = View.GONE
                binding.tvDetailGuid.visibility = View.GONE
                binding.tvDeck.visibility = View.GONE
                binding.view1.visibility = View.GONE

            } else {

                binding.innerConstrainLayout.visibility = View.GONE
                binding.detailToolbar.visibility = View.GONE
            }

            lifecycleScope.launch {
                delay(50)
                val insetsController = requireActivity().window.insetsController
                insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            uiBackToPortrait()


            val paramsPlayer = binding.youtubePlayerView.layoutParams as ConstraintLayout.LayoutParams
            paramsPlayer.width = ViewGroup.LayoutParams.MATCH_PARENT
            paramsPlayer.height = 300.dpToPx()
            binding.youtubePlayerView.layoutParams = paramsPlayer

            val paramsInnerImage = binding.innerConstrainLayout.layoutParams as ConstraintLayout.LayoutParams
            paramsInnerImage.width = ViewGroup.LayoutParams.MATCH_PARENT
            paramsInnerImage.height = 300.dpToPx()
            binding.innerConstrainLayout.layoutParams = paramsInnerImage

            binding.tvGameTitle.visibility = View.VISIBLE
            binding.btnAddToFavorites.visibility = View.VISIBLE
            *//*binding.nestedScrollView.visibility = View.VISIBLE*//*
            binding.innerConstrainLayout.visibility = View.VISIBLE
            binding.detailToolbar.visibility = View.VISIBLE
            binding.tvDetailGenre.visibility = View.VISIBLE
            binding.tvDetailGuid.visibility = View.VISIBLE
            binding.tvDeck.visibility = View.VISIBLE
            binding.view1.visibility = View.VISIBLE

            lifecycleScope.launch {
                delay(50)
                val insetsController = requireActivity().window.insetsController
                insetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }


        }
    }*/


    private fun Int.dpToPx(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (this * density).toInt()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("currentVideoTime", currentVideoTime)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            currentVideoTime = it.getFloat("currentVideoTime", 0f)
        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    @Suppress("DEPRECATION")
    private fun uiLandscape() {
        val window = requireActivity().window
        val insetsController = window.insetsController
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }


    @RequiresApi(Build.VERSION_CODES.R)
    @Suppress("DEPRECATION")
    private fun uiBackToPortrait() {
        val window = requireActivity().window
        val insetsController = window.insetsController
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
    }




}


















/*val genreNames = it.genres?.joinToString(", ") { genre -> genre.name } ?: "No Genre"
binding.tvDetailGenre.text = genreNames*/




/*(activity as AppCompatActivity).supportActionBar?.hide()*/
/*(activity as AppCompatActivity).supportActionBar?.show()*/





/*val decorView = activity?.window?.decorView
decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)





val decorView = activity?.window?.decorView
decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE*/



/*binding.tvDescription.text = Html.fromHtml(it.description?.trim() ?: "")*/



/*val document = Jsoup.parse(it.description?.trim() ?: "")
document.select("a").forEach { aTag ->

    if (aTag.text().trim().isEmpty()) {
        aTag.remove()
    }
}

val cleanedHtml = document.html()
binding.tvDescription.text = Html.fromHtml(cleanedHtml)*/













/* viewModel.currentResult.observe(viewLifecycleOwner) {
            val imageUrl = it.image?.medium_url
            binding.detailGameTitleCover.load(imageUrl)

            binding.tvGameTitle.text = it.name

            binding.tvDescription.text = Html.fromHtml(it.description?.trim() ?: "")
            binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()


            val youtubeIds = viewModel.assignYouTubeIdsToGame(it).youtubeId
            val youtubeId = if (youtubeIds.isNotEmpty()) youtubeIds[0] else null

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


        }*/









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




// Maybe I need it

/*val document = Jsoup.parse(it.description?.trim() ?: "")
            document.select("a").forEach { aTag ->
                val href = aTag.attr("href")
                if (href.startsWith("/")) {
                    aTag.attr("href", "https://www.giantbomb.com$href")
                }
            }

            document.select("a").forEach { aTag ->
                if (aTag.text().trim().isEmpty()) {
                    aTag.remove()
                }
            }

            val cleanedHtml = document.html()
            binding.tvDescription.text = Html.fromHtml(cleanedHtml, Html.FROM_HTML_MODE_LEGACY)
            binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()*/