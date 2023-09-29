package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.games_plus.R
import com.example.games_plus.adapter.SearchAdapter
import com.example.games_plus.databinding.FragmentSearchBinding
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var recyclerState: Parcelable? = null
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = SearchAdapter(requireContext(), emptyList(), viewModel)

        val recView = binding.recSearch
        recView.setHasFixedSize(true)
        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = adapter

        addObservers()

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchGames(binding.editTextSearch.text.toString())
            }
        })

        binding.searchClear.setOnClickListener {
            binding.editTextSearch.text.clear()
        }

        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNav?.visibility = View.VISIBLE

        if (recyclerState != null) {
            binding.recSearch.layoutManager?.onRestoreInstanceState(recyclerState)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addObservers() {
        viewModel.searchList.observe(viewLifecycleOwner) { game ->
            adapter.dataset = game
            adapter.notifyDataSetChanged()
        }
    }

    override fun onPause() {
        super.onPause()
        recyclerState = binding.recSearch.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        if (recyclerState != null) {
            binding.recSearch.layoutManager?.onRestoreInstanceState(recyclerState)
        }
    }
}
