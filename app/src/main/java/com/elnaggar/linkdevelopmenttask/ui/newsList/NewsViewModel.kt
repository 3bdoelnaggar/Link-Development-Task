package com.elnaggar.linkdevelopmenttask.ui.newsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elnaggar.linkdevelopmenttask.data.remote.service.NewsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(val newsService: NewsService) : ViewModel() {
    private val _newsLiveData = MutableLiveData<Result<List<UiNews>>>()
    val newsLiveData: LiveData<Result<List<UiNews>>> = _newsLiveData

    fun loadNews() {
        viewModelScope.launch {
            try {
                val nextWebResult = newsService.fetchArticles(THE_NEXT_WEB)
                val associatedPressResult = newsService.fetchArticles(ASSOCIATED_PRESS)

                val uiList = ArrayList<UiNews>()
                uiList.addAll(nextWebResult.articles.map {
                    UiNews(it.urlToImage, it.title, it.description, it.author, it.publishedAt)
                })
                uiList.addAll(associatedPressResult.articles.map {
                    UiNews(it.urlToImage, it.title, it.description, it.author, it.publishedAt)
                })
                _newsLiveData.value = Result.success(uiList)


            } catch (exception: Exception) {
                _newsLiveData.value = Result.failure(exception)
            }
        }

    }
}

const val THE_NEXT_WEB = "the-next-web"
const val ASSOCIATED_PRESS = "associated-press"