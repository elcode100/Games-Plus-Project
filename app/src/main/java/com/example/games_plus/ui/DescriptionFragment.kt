package com.example.games_plus.ui

import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
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
import com.example.games_plus.R
import com.example.games_plus.databinding.FragmentDescriptionBinding
import com.example.games_plus.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.jsoup.Jsoup


class DescriptionFragment : Fragment() {

    private lateinit var binding: FragmentDescriptionBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBarDescription.visibility = View.VISIBLE
        viewModel.currentGame.observe(viewLifecycleOwner) {
            binding.tvGameTitleBtnBack.text = it.name

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val document = Jsoup.parse(it.description?.trim() ?: "")


                    document.select("a").forEach { aTag ->
                        val href = aTag.attr("href")
                        if (href.startsWith("/")) {
                            aTag.attr("href", "https://www.giantbomb.com$href")
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
                        text.setSpan(ForegroundColorSpan(linkColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }

                    withContext(Dispatchers.Main) {
                        binding.tvDescription.text = text
                        binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
                        binding.progressBarDescription.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        binding.progressBarDescription.visibility = View.GONE

                        Log.e("ERROR", "PARSE ERROR: ${e.message}")
                    }
                }
            }
        }

        OverScrollDecoratorHelper.setUpOverScroll(binding.nestedScrollOverview)


        binding.btnBackToDetails.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvGameTitleBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }



    }
}




/*lifecycleScope.launch(Dispatchers.IO) {
    val document = Jsoup.parse(it.description?.trim() ?: "")

    document.select("a").forEach { aTag ->
        val href = aTag.attr("href")
        if (href.startsWith("/")) {
            aTag.attr("href", "https://www.giantbomb.com$href")
        }
        if (aTag.text().trim().isEmpty()) {
            aTag.remove()
        }
    }

    val cleanedHtml = document.html()
    val spanned = Html.fromHtml(cleanedHtml, Html.FROM_HTML_MODE_LEGACY)

    val descriptionTextView = binding.tvDescription

    val text = SpannableString(spanned)


    val linkColor = ContextCompat.getColor(requireContext(), R.color.game_orange)


    val spans = text.getSpans(0, text.length, URLSpan::class.java)
    for (span in spans) {
        val start = text.getSpanStart(span)
        val end = text.getSpanEnd(span)
        text.setSpan(ForegroundColorSpan(linkColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    withContext(Dispatchers.Main) {
        descriptionTextView.text = text
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}*/






/*lifecycleScope.launch(Dispatchers.IO) {
               val document = Jsoup.parse(it.description?.trim() ?: "")

               document.select("a").forEach { aTag ->
                   val href = aTag.attr("href")
                   if (href.startsWith("/")) {
                       aTag.attr("href", "https://www.giantbomb.com$href")
                   }
                   if (aTag.text().trim().isEmpty()) {
                       aTag.remove()
                   }
               }

               val cleanedHtml = document.html()
               val spanned = Html.fromHtml(cleanedHtml, Html.FROM_HTML_MODE_LEGACY)

               withContext(Dispatchers.Main) {
                   binding.tvDescription.text = spanned
                   binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
               }
           }*/