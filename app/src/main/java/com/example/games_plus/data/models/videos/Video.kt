package com.example.games_plus.data.models.videos

import com.squareup.moshi.Json

data class Video(
    val id: Int = 0,
    val name: String = "",
    @Json(name = "api_detail_url") val apiDetailUrl: String = ""
)


/*
@Json(name = "youtube_id") val youtubeId: String = ""*/
/*val image: VideoImage? = null,*/
