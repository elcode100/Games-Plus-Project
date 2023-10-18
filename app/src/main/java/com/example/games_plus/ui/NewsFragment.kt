package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.example.games_plus.adapter.NewsFragmentAdapter
import com.example.games_plus.databinding.FragmentNewsBinding
import com.example.games_plus.ui.viewmodels.MainViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadNews()
        /*viewModel.loadNewsByCategories(listOf(58, 65, 68))*/

        OverScrollDecoratorHelper.setUpOverScroll(binding.recNews, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)



        val newsAdapter = NewsFragmentAdapter(emptyList(), viewModel)
        binding.recNews.adapter = newsAdapter
        binding.recNews.setHasFixedSize(true)




        binding.progressBarNews.visibility = View.VISIBLE

        viewModel.dataListNews.observe(viewLifecycleOwner) { news ->

            binding.progressBarNews.visibility = View.GONE

            newsAdapter.dataset = news
            newsAdapter.notifyDataSetChanged()


        }


    }


}