package com.example.games_plus.data.models.news

import com.squareup.moshi.Json

data class Image(
    @Json(name = "square_tiny") val squareTiny: String,
    @Json(name = "screen_tiny") val screenTiny: String,
    @Json(name = "square_small") val squareSmall: String,
    val original: String
)
