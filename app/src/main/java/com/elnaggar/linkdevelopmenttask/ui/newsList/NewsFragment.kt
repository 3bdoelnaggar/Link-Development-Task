package com.elnaggar.linkdevelopmenttask.ui.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elnaggar.linkdevelopmenttask.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_fragment, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadNews()
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                render(it)
            }
        }
    }

    private fun render(result: Result<List<UiNews>>) {
        val uiNews = result.getOrNull()
        if (uiNews != null) {
            val recycleView: RecyclerView = view as RecyclerView
            recycleView.adapter = NewsRecyclerViewAdapter(uiNews) { item ->
                viewModel.selectedUiNews = item
                findNavController().navigate(R.id.newsDetailsFragment)
            }

        } else {
            Toast.makeText(requireContext(), "ERROR LOADING NEWS", Toast.LENGTH_SHORT).show()
        }
    }
}