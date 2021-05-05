package com.elnaggar.linkdevelopmenttask.data.remote.service

import com.elnaggar.linkdevelopmenttask.data.remote.entites.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    //https://newsapi.org/v1/articles?source=the-next-web&amp&apiKey=533af958594143758318137469b41ba9
    @GET("articles")
    suspend fun fetchArticles(
        @Query("source") source: String,
        @Query("apiKey") apiKey: String = "533af958594143758318137469b41ba9"
    ): ArticlesResponse
}