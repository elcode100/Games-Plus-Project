package com.example.games_plus.data.remote

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import com.example.games_plus.data.model.GameResponse
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query






const val BASE_URL = "https://www.giantbomb.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()





interface GamesApiService {


    @GET("games")
    suspend fun getSpecificGames(
        @Query("api_key") apiKey: String = "c65414d8f9676a0036eb9587584863587cc8b250",
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,description,image,genres,videos",
        @Query("filter") filter: String = "id:41484|36765|81128|20538",
        @Query("limit") limit: Int = 4
    ): GameResponse


    @GET("games")
    suspend fun getRecentGames(
        @Query("api_key") apiKey: String = "c65414d8f9676a0036eb9587584863587cc8b250",
        @Query("format") format: String = "json",
        @Query("field_list") fields: String = "id,name,description,image,genres,videos",
        @Query("filter") filter: String = "original_release_date:2010-01-01|2023-01-01",
        @Query("sort") sort: String = "original_release_date:desc",
        @Query("limit") limit: Int = 150
    ): GameResponse



}

object GamesApi {
    val retrofitService: GamesApiService by lazy { retrofit.create(GamesApiService::class.java) }
}















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
