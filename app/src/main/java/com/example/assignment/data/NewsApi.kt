package com.example.assignment.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsApi(
    @SerialName("articles")
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
    )