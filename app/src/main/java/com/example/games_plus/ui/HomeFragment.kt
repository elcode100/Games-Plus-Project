package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.games_plus.R
import com.example.games_plus.adapter.HomeAdapter
import com.example.games_plus.adapter.HomeAdapter2
import com.example.games_plus.adapter.HomeAdapter3
import com.example.games_plus.databinding.FragmentHomeBinding
import com.example.games_plus.ui.viewmodels.AuthViewModel
import com.example.games_plus.ui.viewmodels.MainViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val window = activity?.window
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.recGames.setHasFixedSize(true)

        viewModel.loadAllGames()
        viewModel.loadUpcomingGames()
        /*viewModel.loadMobileGames()*/
        viewModel.loadFavoriteGames()
        addObservers()

        val testPic = R.drawable.cyberpunk_2077_phantom_liberty_placeholder


            binding.imageViewTest.setImageResource(testPic)

        /*binding.imageViewXx.setImageResource(R.drawable.atomic_heart_cover_placeholder)*/





            /*binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                if (scrollY > 30) {

                    binding.customTitle.visibility = View.GONE
                } else {

                    binding.customTitle.visibility = View.VISIBLE
                }
            })
    */






        /*
                binding.btnLogout.setOnClickListener {
                    authViewModel.logout()
                }*/





    }

    private fun areAllDataListsLoaded(): Boolean {
        return viewModel.dataList.value != null && viewModel.dataListUpcomingGames.value != null /*&& viewModel.dataListMobileGames.value != null*/
    }



    @SuppressLint("NotifyDataSetChanged")
    fun addObservers() {


        OverScrollDecoratorHelper.setUpOverScroll(binding.recGames, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
        OverScrollDecoratorHelper.setUpOverScroll(binding.recGames2, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
        OverScrollDecoratorHelper.setUpOverScroll(binding.scrollViewHome)


        val itemRecView1 = HorizontalItemDecoration(8.dpToPx())
        binding.recGames.addItemDecoration(itemRecView1)

        val itemRecView2 = HorizontalItemDecoration(8.dpToPx())
        binding.recGames2.addItemDecoration(itemRecView2)



        binding.progressBar.visibility = View.VISIBLE
        binding.recGames.visibility = View.INVISIBLE
        binding.recGames2.visibility = View.INVISIBLE
        binding.tvBestGames.visibility = View.INVISIBLE
        binding.tvUpcomingGames.visibility = View.INVISIBLE
        binding.imageViewTest.visibility = View.INVISIBLE
        binding.cardTestPic.visibility = View.INVISIBLE
        binding.customTitle.visibility = View.INVISIBLE





        val adapter = HomeAdapter(this.requireContext(), emptyList(), viewModel)
        binding.recGames.adapter = adapter





        viewModel.dataList.observe(viewLifecycleOwner) { games ->
            adapter.dataset = games
            adapter.notifyDataSetChanged()

            if (areAllDataListsLoaded()) {
                binding.progressBar.visibility = View.GONE
                binding.recGames.visibility = View.VISIBLE
                binding.recGames2.visibility = View.VISIBLE
                binding.tvBestGames.visibility = View.VISIBLE
                binding.tvUpcomingGames.visibility = View.VISIBLE
                binding.imageViewTest.visibility = View.VISIBLE
                binding.cardTestPic.visibility = View.VISIBLE
                binding.customTitle.visibility = View.VISIBLE
            }
        }





        authViewModel.currentUser.observe(viewLifecycleOwner) {
            if (it == null) {
                findNavController().navigate(R.id.loginFragment)
            }
        }






        val adapter2 = HomeAdapter2(emptyList(), viewModel)
        binding.recGames2.adapter = adapter2


        viewModel.dataListUpcomingGames.observe(viewLifecycleOwner) { games ->
            adapter2.dataset2 = games
            adapter2.notifyDataSetChanged()

            if (areAllDataListsLoaded()) {
                binding.progressBar.visibility = View.GONE
                binding.recGames.visibility = View.VISIBLE
                binding.recGames2.visibility = View.VISIBLE
                binding.tvBestGames.visibility = View.VISIBLE
                binding.tvUpcomingGames.visibility = View.VISIBLE
                binding.imageViewTest.visibility = View.VISIBLE
                binding.cardTestPic.visibility = View.VISIBLE
                binding.customTitle.visibility = View.VISIBLE
            }

        }




        /*val adapter3 = HomeAdapter3(emptyList(), viewModel)
        binding.recGames2.adapter = adapter3


        viewModel.dataListMobileGames.observe(viewLifecycleOwner) { games ->
            adapter3.dataset3 = games
            adapter3.notifyDataSetChanged()

            if (areAllDataListsLoaded()) {
                binding.progressBar.visibility = View.GONE
                binding.recGames.visibility = View.VISIBLE
                binding.recGames2.visibility = View.VISIBLE
                binding.tvBestGames.visibility = View.VISIBLE
                binding.tvUpcomingGames.visibility = View.VISIBLE
                binding.imageViewTest.visibility = View.VISIBLE
                binding.cardTestPic.visibility = View.VISIBLE
                binding.customTitle.visibility = View.VISIBLE
            }

        }*/









    }


    fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }






}











/*val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
layoutManager.scrollToPosition(Integer.MAX_VALUE / 2)
binding.recGames2.layoutManager = layoutManager



binding.recGames2.addOnScrollListener(object : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        if (firstVisibleItemPosition == 0) {

            recyclerView.scrollToPosition(Integer.MAX_VALUE / 2)
        } else if (lastVisibleItemPosition == Integer.MAX_VALUE) {

            recyclerView.scrollToPosition(Integer.MAX_VALUE / 2)
        }
    }
})*/
















/*val recView = binding.recGames
viewModel.dataList.observe(viewLifecycleOwner) {

    recView.adapter = HomeAdapter(this.requireContext(), it, viewModel)

    binding.recGames.apply {
        set3DItem(true)
        setAlpha(true)
        setInfinite(true)

    }
}*/






/*viewModel.dataList.observe(viewLifecycleOwner) { games ->
            adapter.dataset = games
            adapter.notifyDataSetChanged()
        }*/




/*val adapter = HomeAdapter(this.requireContext(), emptyList(), viewModel)
    binding.recGames.adapter = adapter
    binding.recGames.apply {
        set3DItem(true)
        setAlpha(true)
        setInfinite(true)

    }*/



/*
binding.apply {
    recGames.adapter = adapter
    recGames.set3DItem(true)
    recGames.setAlpha(true)
    recGames.setInfinite(true)

}*/



