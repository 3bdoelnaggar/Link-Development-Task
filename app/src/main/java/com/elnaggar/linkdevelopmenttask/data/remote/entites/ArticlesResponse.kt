package com.elnaggar.linkdevelopmenttask.data.remote.entites


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticlesResponse(
    @Json(name = "articles")
    val articles: List<Article>,
    @Json(name = "sortBy")
    val sortBy: String,
    @Json(name = "source")
    val source: String,
    @Json(name = "status")
    val status: String
)