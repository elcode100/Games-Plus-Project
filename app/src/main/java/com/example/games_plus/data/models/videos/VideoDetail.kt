package com.example.games_plus.data.models.videos

import com.squareup.moshi.Json

data class VideoDetail(
    val id: Int = 0,
    val name: String = "",
    val user: String? = "",
    val deck: String? = "",
    val image: VideoDetailImage? = null,
    val premium: Boolean? = false,
    @Json(name = "publish_date") val publishDate: String? = "",
    @Json(name = "high_url") val highUrl: String? = "",
    @Json(name = "youtube_id") val youtubeId: String? = ""
)
