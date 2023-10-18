package com.example.games_plus.data.models.reviews

import com.squareup.moshi.Json

data class UserReview(

    val score: Int? = 0,
    val deck: String? = "",
    val description: String? = "",
)
/*   @Json(name = "review_date") val reviewDate: String? = ""*/
/* val id: Int? = 0,
   val guid: String? = "",
   val game: GameInfo? = null,
   val reviewer: String? = "",*/