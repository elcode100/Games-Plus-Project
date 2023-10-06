package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.games_plus.R
import com.example.games_plus.adapter.LibraryAdapter
import com.example.games_plus.adapter.SearchAdapter
import com.example.games_plus.databinding.FragmentLibraryBinding
import com.example.games_plus.ui.viewmodels.MainViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var recyclerState: Parcelable? = null
    private lateinit var adapter: LibraryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = activity?.window
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)

        adapter = LibraryAdapter(this.requireContext(), emptyList(), viewModel)

        val recView = binding.recLibrary
        recView.setHasFixedSize(true)
        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = adapter

        OverScrollDecoratorHelper.setUpOverScroll(binding.recLibrary, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        val itemRecView1 = ItemLibrary(8.dpToPx())
        binding.recLibrary.addItemDecoration(itemRecView1)


        addObservers()
        viewModel.loadFavoriteGames()



    }

    fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }




    @SuppressLint("NotifyDataSetChanged")
    private fun addObservers() {



        viewModel.favoriteGames.observe(viewLifecycleOwner) {
            adapter.dataset = it
            adapter.notifyDataSetChanged()

        }
    }

    override fun onPause() {
        super.onPause()
        recyclerState = binding.recLibrary.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        if (recyclerState != null) {
            binding.recLibrary.layoutManager?.onRestoreInstanceState(recyclerState)
        }
    }




}





/*
val window = activity?.window
window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)*/
