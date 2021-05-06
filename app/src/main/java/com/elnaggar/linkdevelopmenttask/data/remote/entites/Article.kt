package com.elnaggar.linkdevelopmenttask.data.remote.entites


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "author")
    val author: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "publishedAt")
    val publishedAt: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "urlToImage")
    val urlToImage: String
)