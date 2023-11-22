package com.example.games_plus.data.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.games_plus.data.API_KEY
import com.example.games_plus.data.models.developers.DeveloperResponse
import com.example.games_plus.data.models.games.GameResponse
import com.example.games_plus.data.models.genres.GenreResponse
import com.example.games_plus.data.models.reviews.UserReviewResponse
import com.example.games_plus.data.models.videos.VideoDetailResponse
import com.example.games_plus.data.models.videos.VideoResponse
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
val today: LocalDate = LocalDate.now()

@RequiresApi(Build.VERSION_CODES.O)
val tomorrow: LocalDate = today.plusDays(1)

@RequiresApi(Build.VERSION_CODES.O)
val endDate: LocalDate = today.plusYears(3)

@RequiresApi(Build.VERSION_CODES.O)
val thirtyDaysAgo: LocalDate = today.minusDays(30)


const val BASE_URL = "https://www.giantbomb.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .readTimeout(30, TimeUnit.SECONDS)
    .connectTimeout(30, TimeUnit.SECONDS)
    .build()



private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()





interface GamesApiService {


    @GET("games")
    suspend fun getBestGames(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,deck,description,image,guid,original_release_date,platforms",
        @Query("filter") filter: String
    ): GameResponse


    @GET("user_reviews/")
    suspend fun getUserReviews(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("filter") filter: String,
        @Query("field_list") fields: String = "score,deck,description"
    ): UserReviewResponse





    @GET("search/")
    suspend fun getSearchGameResult(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("query") query: String,
        @Query("resources") resources: String = "game",
        @Query("field_list") fields: String = "id,name,description,deck,image,guid,original_release_date,platforms"
    ): GameResponse




    @GET("game/{guid}/")
    suspend fun getGameGenres(
        @Path("guid") guid: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "genres"
    ): GenreResponse


    @GET("game/{guid}/")
    suspend fun getGameDevelopers(
        @Path("guid") guid: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "developers"
    ): DeveloperResponse


    @GET("game/{guid}/")
    suspend fun getGameVideos(
        @Path("guid") guid: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "videos,api_detail_url"
    ): VideoResponse


    @GET("video/{guid}/")
    suspend fun getVideoDetails(
        @Path("guid") guid: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,user,deck,image,premium,publish_date,high_url,youtube_id"
    ): VideoDetailResponse


    @RequiresApi(Build.VERSION_CODES.O)
    @GET("games")
    suspend fun getLast30DaysGames(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,deck,description,image,guid,original_release_date,platforms,expected_release_day,expected_release_month,expected_release_year",
        @Query("filter") filter: String = "original_release_date:$thirtyDaysAgo|$today",
        @Query("sort") sort: String = "original_release_date:desc",
        @Query("limit") limit: Int = 40,
        @Query("offset") offset: Int = 0
    ): GameResponse






    @RequiresApi(Build.VERSION_CODES.O)
    @GET("games")
    suspend fun getUpcomingGames(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,deck,description,image,guid,original_release_date,platforms,expected_release_day,expected_release_month,expected_release_year",
        @Query("filter") filter: String = "original_release_date:$tomorrow|$endDate",
        @Query("sort") sort: String = "original_release_date:asc",
        @Query("limit") limit: Int = 60,
        @Query("offset") offset: Int = 0
    ): GameResponse





    @GET("games")
    suspend fun getMobileGames(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,deck,description,image,guid,original_release_date,platforms",
        @Query("filter") filter: String = "original_release_date:2016-01-01|2023-01-01",
        @Query("sort") sort: String = "original_release_date:desc",
        @Query("limit") limit: Int = 100
    ): GameResponse




}

object GamesApi {
    val retrofitService: GamesApiService by lazy { retrofit.create(GamesApiService::class.java) }



}














/*@GET("{api_detail_url}")
suspend fun getVideoDetails(
    @Path("api_detail_url", encoded = true) apiDetailUrl: String,
    @Query("api_key") apiKey: String = API_KEY,
    @Query("format") format: String = "json"
): VideoDetailResponse*/




/*@Query("sort") sort: String = "original_release_date:desc"*/

/*@Query("sort") sort: String = "rating:desc"*/



/*interface GamesApiService {

    @GET("games")
    suspend fun getGameResults(
        @Query("api_key") apiKey: String = "c65414d8f9676a0036eb9587584863587cc8b250",
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,description,image,videos",
        @Query("filter") filter: String = "id:41484|36765|81128|20538",
        @Query("sort") sort: String = "original_release_date:desc",
        @Query("limit") limit: Int = 100
    ): GameResponse

    @GET("games")
    suspend fun getGameVideoResults(
        @Query("api_key") apiKey: String = "c65414d8f9676a0036eb9587584863587cc8b250",
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,description,videos"
    ): GameResponse

}

object GamesApi {
    val retrofitService: GamesApiService by lazy { retrofit.create(GamesApiService::class.java) }
}*/






/*@GET("games")
    suspend fun getSpecificGames(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,description,image,guid",
        @Query("filter") filter: String = "id:41484|36765|81128|20538", *//*|78967|75845|32327*//*

    ): GameResponse*/













/*
interface GamesApiService {



        @GET("games")
        suspend fun getGameResults(
            @Query("api_key") apiKey: String = "c65414d8f9676a0036eb9587584863587cc8b250",
            @Query("format") format: String = "json",
            @Query("field_list") fields: String = "id,name,description,image,videos",
            @Query("filter") filter: String = "original_release_date:2010-01-01|2023-01-01",
            @Query("sort") sort: String = "original_release_date:desc",
            @Query("limit") limit: Int = 100
        ): GameResponse




    @GET("games")
    suspend fun getGameVideoResults(
        @Query("api_key") apiKey: String = "c65414d8f9676a0036eb9587584863587cc8b250",
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,description,videos"
    ): GameResponse

}

object GamesApi {
    val retrofitService: GamesApiService by lazy { retrofit.create(GamesApiService::class.java) }
}
*/









































/*const val BASE_URL = "https://api.igdb.com/v4/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val authInterceptor = Interceptor { chain ->
    val original = chain.request()
    val requestBuilder = original.newBuilder()
        .header("Authorization", "Bearer 3lf0fjlvwf9m8o6iehjg3yk7wwn7qq")
        .header("Client-ID", "agohwpdbfj9n7sef41vx9euz9nw2w4")
        .method(original.method(), original.body())
    val request = requestBuilder.build()
    chain.proceed(request)
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface GamesApiService {
    @GET("games")
    suspend fun getGameResults(@Query("page_size") size: Int): Response

    @GET("games/{gameId}/videos")
    suspend fun getGameVideos(@Path("gameId") gameId: Int): Response
}

object GamesApi {
    val retrofitService: GamesApiService by lazy { retrofit.create(GamesApiService::class.java) }
}*/


































/*
const val BASE_URL = "https://api.rawg.io/api/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


val apiKeyInterceptor = Interceptor { chain ->
    val original = chain.request()
    val originalHttpUrl = original.url()
    val url = originalHttpUrl.newBuilder()
        .addQueryParameter("key", "27a7cf612695453896756ef2390df954")
        .build()

    val requestBuilder = original.newBuilder().url(url)
    val request = requestBuilder.build()
    chain.proceed(request)
}


private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(apiKeyInterceptor)
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()


interface GamesApiService {

    @GET("games")
    suspend fun getGameResults(@Query("page_size") size: Int): Response

    @GET("games/{gameId}/videos")
    suspend fun getGameVideos(@Path("gameId") gameId: Int): Response


}


object GamesApi {
    val retrofitService: GamesApiService by lazy { retrofit.create(GamesApiService::class.java) }

}*/
