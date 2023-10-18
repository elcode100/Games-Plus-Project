package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.games_plus.adapter.VideosAdapter
import com.example.games_plus.databinding.FragmentVideosBinding
import com.example.games_plus.ui.viewmodels.MainViewModel


class VideosFragment : Fragment() {

    private lateinit var binding: FragmentVideosBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       viewModel.loadVideos()


        val adapter = VideosAdapter(emptyList(), viewModel)
        binding.recVideos.adapter = adapter
        binding.recVideos.setHasFixedSize(true)


        binding.progressBarVideos.visibility = View.VISIBLE
        viewModel.dataListVideos.observe(viewLifecycleOwner) { videos ->
            binding.progressBarVideos.visibility = View.GONE


            adapter.dataset = videos
            adapter.notifyDataSetChanged()




        }


        viewModel.currentGame.observe(viewLifecycleOwner) {
            binding.tvGameTitleBtnBack.text = it.name
        }




        binding.btnBackToDetails.setOnClickListener {
            findNavController().navigateUp()

        }

        binding.tvGameTitleBtnBack.setOnClickListener {
            findNavController().navigateUp()

        }



    }




}







/*binding.progressBarNews.visibility = View.VISIBLE*/

/*viewModel.dataListNews.observe(viewLifecycleOwner) { videos ->

    *//*binding.progressBarNews.visibility = View.GONE*//*

            videoAdapter.dataset = videos
            videoAdapter.notifyDataSetChanged()


        }*/