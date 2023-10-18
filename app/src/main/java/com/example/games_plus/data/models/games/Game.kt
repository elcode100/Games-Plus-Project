package com.example.games_plus.data.models.games

import com.example.games_plus.data.models.genres.Genre
import com.example.games_plus.data.models.platforms.Platform
import com.squareup.moshi.Json

data class Game(
    val id: Int = 0,
    val name: String = "",
    var guid: String = "",
    val description: String? = "",
    val deck: String? = "",
    val image: ImageDetail? = null,
    var genres: List<Genre>? = listOf(),
    val platforms: List<Platform>? = listOf(),
    @Json(name = "original_release_date") val originalReleaseDate: String? = "",
    @Json(name = "expected_release_day") val expectedReleaseDay: Int? = 0,
    @Json(name = "expected_release_month") val expectedReleaseMonth: Int? = 0,
    @Json(name = "expected_release_year") val expectedReleaseYear: Int? = 0,
    val youId: List<String> = listOf(),
    var averageUserScore: Double? = null
)
