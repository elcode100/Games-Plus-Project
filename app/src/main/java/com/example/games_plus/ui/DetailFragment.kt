package com.example.games_plus.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.games_plus.R
import com.example.games_plus.databinding.FragmentBookmarkBinding
import com.example.games_plus.databinding.FragmentDetailBinding
import com.example.games_plus.databinding.FragmentSignUpBinding

class DetailFragment : Fragment() {


    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}