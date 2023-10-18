package com.example.games_plus.data.models.genres

import com.squareup.moshi.Json

data class Genre(
    val id: Int = 0,
    val name: String = "",
    @Json(name = "site_detail_url")val siteDetailUrl: String? = ""
)