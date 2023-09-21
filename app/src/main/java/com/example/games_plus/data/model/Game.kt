package com.example.games_plus.data.model

data class Game(
    val id: Int = 0,
    val name: String = "",
    val description: String? = "",
    val image: ImageDetail? = null,
    val youtubeId: List<String> = listOf(),
    val genres: List<Genre>? = listOf(),
    val videos: List<Video>? = listOf()
)



