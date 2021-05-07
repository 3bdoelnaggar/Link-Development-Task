package com.elnaggar.linkdevelopmenttask.ui.newsDeatils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.elnaggar.linkdevelopmenttask.R
import com.elnaggar.linkdevelopmenttask.databinding.NewsDetailsFragmentBinding
import com.elnaggar.linkdevelopmenttask.ui.newsList.NewsViewModel
import com.elnaggar.linkdevelopmenttask.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment(R.layout.news_details_fragment) {
    private val viewModel: NewsViewModel by activityViewModels()

    private val binding by viewBinding(NewsDetailsFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = viewModel.selectedUiNews
        if (item != null) {
            with(binding) {
                authorTextView.text = item.by
                dateTextView.text = item.date
                titleImageView.text = item.title
                imageView.load(item.imageUrl)
                contentTextView.text = item.content

            }
        }

    }

}