package com.elnaggar.linkdevelopmenttask.ui.newsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elnaggar.linkdevelopmenttask.data.remote.entites.Article
import com.elnaggar.linkdevelopmenttask.data.remote.service.NewsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


const val THE_NEXT_WEB = "the-next-web"
const val ASSOCIATED_PRESS = "associated-press"

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsService: NewsService) : ViewModel() {
    private val _newsLiveData = MutableLiveData<Result<List<UiNews>>>()
    val newsLiveData: LiveData<Result<List<UiNews>>> = _newsLiveData
    var selectedUiNews: UiNews? = null

    fun loadNews() {
        viewModelScope.launch {
            try {
                val nextWebJob = async { newsService.fetchArticles(THE_NEXT_WEB) }
                val associatedPressJob = async { newsService.fetchArticles(ASSOCIATED_PRESS) }

                val nextWebResult = nextWebJob.await()
                val associatedPressResult = associatedPressJob.await()

                val uiList = ArrayList<UiNews>()
                uiList.addAll(nextWebResult.articles.mapNotNull {
                    it.toUiNews()
                })
                uiList.addAll(associatedPressResult.articles.mapNotNull {
                    it.toUiNews()
                })
                _newsLiveData.value = Result.success(uiList)

            } catch (exception: HttpException) {
                _newsLiveData.value = Result.failure(Exception("Too Many Requests"))
            } catch (exception: IOException) {
                _newsLiveData.value = Result.failure(Exception("Check your internet connection"))
            } catch (exception: Exception) {
                _newsLiveData.value = Result.failure(Exception("Error Loading News"))
            }
        }

    }

}

private fun Article.toUiNews(): UiNews? {

    if (urlToImage != null && title != null && description != null) {
        //2021-05-07T13:06:07Z
        val serverDateText = publishedAt
        var uiDate = ""
        if (serverDateText != null) {
            val serverDateFormatString = "yyyy-mm-dd"
            val uiDateFormat = "MMMM dd,yyyy"
            val serverDate =
                SimpleDateFormat(serverDateFormatString, Locale.US).parse(serverDateText);
            if (serverDate != null) {
                val uiFormat = SimpleDateFormat(uiDateFormat, Locale.US).format(serverDate)
                uiDate = uiFormat
            }
        }

        return UiNews(urlToImage, title, description, "By $author", uiDate)
    } else {
        return null
    }

}

