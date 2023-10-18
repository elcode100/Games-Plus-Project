package com.example.games_plus.data.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.games_plus.data.API_KEY_NEWS
import com.example.games_plus.data.models.news.ArticleResponse
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
val currentDate: LocalDate = LocalDate.now()
@RequiresApi(Build.VERSION_CODES.O)
val oneYearAgo: LocalDate = currentDate.minusYears(1)

@RequiresApi(Build.VERSION_CODES.O)
val dateFilter = "publish_date:$oneYearAgo|$currentDate"

const val BASE_URL_NEWS = "https://www.gamespot.com/api/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_NEWS)
    .build()

interface NewsApiService {
    @RequiresApi(Build.VERSION_CODES.O)
    @GET("articles")
    suspend fun getArticles(
        @Query("api_key") apiKey: String = API_KEY_NEWS,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,authors,title,deck,lede,body,image,categories,publish_date,update_date,site_detail_url",
        @Query("limit") limit: Int = 80,
        @Query("filter") filter: String = dateFilter,
        @Query("sort") sort: String = "publish_date:desc"
    ): ArticleResponse


    @RequiresApi(Build.VERSION_CODES.O)
    @GET("articles")
    suspend fun getLatestNews(
        @Query("api_key") apiKey: String = API_KEY_NEWS,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,authors,title,deck,lede,body,image,categories,publish_date,update_date,site_detail_url",
        @Query("limit") limit: Int = 8,
        @Query("filter") filter: String = dateFilter,
        @Query("sort") sort: String = "publish_date:desc"
    ): ArticleResponse




}

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}



