package com.example.games_plus.ui

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.games_plus.R
import com.example.games_plus.databinding.FragmentHomeBinding
import com.example.games_plus.databinding.FragmentVideoDetailBinding
import com.example.games_plus.ui.viewmodels.AuthViewModel
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class VideoDetailFragment : Fragment() {

    private lateinit var binding: FragmentVideoDetailBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var currentVideoTime: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNav?.visibility = View.GONE



        viewModel.currentVideo.observe(viewLifecycleOwner) {

            binding.tvVideoTitle.text = it.name
            binding.tvDeck.text = it.deck


            val youtubeId = it.youtubeId

            binding.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    if (youtubeId != null) {
                        youTubePlayer.cueVideo(youtubeId, currentVideoTime)
                    }
                    binding.youtubePlayerView.setOnClickListener {
                        youTubePlayer.play()
                    }
                }
            })


        }

        binding.btnBackToVideos.setOnClickListener { findNavController().navigateUp() }
        binding.tvBackToVideos.setOnClickListener { findNavController().navigateUp() }


    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {


            if (binding.youtubePlayerView.visibility == View.VISIBLE) {

                uiLandscape()

                val params = binding.youtubePlayerView.layoutParams as ConstraintLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                binding.youtubePlayerView.layoutParams = params

                binding.tvVideoTitle.visibility = View.GONE
                binding.tvDeck.visibility = View.GONE
                binding.toolbar.visibility = View.GONE

            } /*else {

                binding.innerConstrainLayout.visibility = View.GONE
                binding.detailToolbar.visibility = View.GONE
            }*/

            lifecycleScope.launch {
                delay(50)
                val insetsController = requireActivity().window.insetsController
                insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                insetsController?.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            uiBackToPortrait()


            val paramsPlayer =
                binding.youtubePlayerView.layoutParams as ConstraintLayout.LayoutParams
            paramsPlayer.width = ViewGroup.LayoutParams.MATCH_PARENT
            paramsPlayer.height = 300.dpToPx()
            binding.youtubePlayerView.layoutParams = paramsPlayer



            binding.tvVideoTitle.visibility = View.VISIBLE
            binding.tvDeck.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE

            lifecycleScope.launch {
                delay(50)
                val insetsController = requireActivity().window.insetsController
                insetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                insetsController?.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }


        }
    }


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


    /*@RequiresApi(Build.VERSION_CODES.R)
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
        insetsController?.setSystemBarsAppearance(
            0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )


    }*/

    @RequiresApi(Build.VERSION_CODES.R)
    @Suppress("DEPRECATION")
    private fun uiLandscape() {
        val window = requireActivity().window
        val insetsController = window.insetsController
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        insetsController?.setSystemBarsAppearance(
            0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Suppress("DEPRECATION")
    private fun uiBackToPortrait() {
        val window = requireActivity().window
        val insetsController = window.insetsController
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

        insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }






}