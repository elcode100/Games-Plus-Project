package com.example.games_plus.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.games_plus.databinding.FragmentDetailBinding
import com.example.games_plus.ui.viewmodels.MainViewModel


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.currentResult.observe(viewLifecycleOwner) {

            binding.imgCover.load(it.backgroundImage.toUri().buildUpon().scheme("https").build())
            binding.tvSong.text = it.name
            binding.tvArtist.text = it.description




        }
    }


}