package com.elnaggar.linkdevelopmenttask.ui.newsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elnaggar.linkdevelopmenttask.data.remote.entites.Article
import com.elnaggar.linkdevelopmenttask.data.remote.service.NewsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.format.DateTimeFormatter
import javax.inject.Inject


const val THE_NEXT_WEB = "the-next-web"
const val ASSOCIATED_PRESS = "associated-press"

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsService: NewsService) : ViewModel() {
    private val _newsLiveData = MutableLiveData<Result<List<UiNews>>>()
    val newsLiveData: LiveData<Result<List<UiNews>>> = _newsLiveData
    var selectedUiNews:UiNews? =null

    fun loadNews() {
        viewModelScope.launch {
            try {
                val nextWebResult = newsService.fetchArticles(THE_NEXT_WEB)
                val associatedPressResult = newsService.fetchArticles(ASSOCIATED_PRESS)

                val uiList = ArrayList<UiNews>()
                uiList.addAll(nextWebResult.articles.map {
                    it.toUiNews()
                })
                uiList.addAll(associatedPressResult.articles.map {
                    it.toUiNews()
                })
                _newsLiveData.value = Result.success(uiList)


            }catch (exception:HttpException){
                _newsLiveData.value = Result.failure(exception)
            }
            catch (exception: Exception) {
                _newsLiveData.value = Result.failure(exception)
            }
        }

    }

}

private fun Article.toUiNews(): UiNews {
    return UiNews(urlToImage, title, description, "By $author", publishedAt)

}

