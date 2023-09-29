package com.example.games_plus.data.model

data class Game(
    val id: Int = 0,
    val name: String = "",
    val guid: String = "",
    val description: String? = "",
    val image: ImageDetail? = null,
    var genres: List<Genre>? = listOf(),
    val youtubeId: List<String> = listOf(),
    var index: Int? = null
)



/*
val videos: List<Video>? = listOf(),*/
