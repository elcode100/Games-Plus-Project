package com.example.games_plus.data.remote

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import com.example.games_plus.data.model.Response
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

}