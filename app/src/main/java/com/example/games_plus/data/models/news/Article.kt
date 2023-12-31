package com.example.games_plus.data.models.news

import com.squareup.moshi.Json

data class Article(
    val id: Int = 0,
    val authors: String? = "",
    val title: String? = "",
    val deck: String? = "",
    val lede: String? = "",
    val body: String? = "",
    val image: Image? = null,
    val categories: List<Category> = listOf(),
    @Json(name = "publish_date") val publishDate: String = "",
    @Json(name = "update_date") val updateDate: String = "",
    @Json(name = "site_detail_url") val siteDetailUrl: String = ""
)
