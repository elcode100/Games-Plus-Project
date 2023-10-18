package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.games_plus.R
import com.example.games_plus.databinding.FragmentHomeBinding
import com.example.games_plus.databinding.FragmentNewsBinding
import com.example.games_plus.databinding.FragmentNewsDetailBinding
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.jsoup.Jsoup


class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.currentArticle.observe(viewLifecycleOwner) {

            val imageUrl = it.image?.original
            binding.ivArticleImage.load(imageUrl)
            binding.tvArticleTitle.text = it.title
            binding.tvDeck.text = it.deck
            binding.tvPublishesBy.text = "By ${it.authors}"
            binding.tvPublishDate.text = " on ${it.publishDate}"


            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val document = Jsoup.parse(it.body?.trim() ?: "")


                    document.select("a").forEach { aTag ->
                        val href = aTag.attr("href")
                        if (href.startsWith("/")) {
                            aTag.attr("href", "https://www.gamespot.com$href")
                        }
                        if (aTag.text().trim().isEmpty()) {
                            aTag.remove()
                        }
                    }

                    val cleanedHtml = document.html()
                    val spanned = Html.fromHtml(cleanedHtml, Html.FROM_HTML_MODE_LEGACY)
                    val text = SpannableString(spanned)


                    val linkColor = ContextCompat.getColor(requireContext(), R.color.game_orange)
                    val spans = text.getSpans(0, text.length, URLSpan::class.java)
                    for (span in spans) {
                        val start = text.getSpanStart(span)
                        val end = text.getSpanEnd(span)
                        text.setSpan(
                            ForegroundColorSpan(linkColor),
                            start,
                            end,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }

                    withContext(Dispatchers.Main) {
                        binding.tvBody.text = text

                        binding.progressBar.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE

                        Log.e("ERROR", "PARSE ERROR: ${e.message}")
                    }
                }
            }







        }

        binding.tvBtnBack.setOnClickListener { findNavController().navigateUp() }
        binding.btnBackArrow.setOnClickListener { findNavController().navigateUp() }
        OverScrollDecoratorHelper.setUpOverScroll(binding.scroll)







    }


}