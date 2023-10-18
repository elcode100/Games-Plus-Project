package com.example.games_plus.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.games_plus.data.api.NewsApi
import com.example.games_plus.data.models.news.Article
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RepositoryNews(private val api: NewsApi) {


    private val _articleResult = MutableLiveData<List<Article>>()
    val articleResult: LiveData<List<Article>>
        get() = _articleResult


    private val _articleLatestNews = MutableLiveData<List<Article>>()
    val articleLatestNews: LiveData<List<Article>>
        get() = _articleLatestNews



    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getArticles() {
        coroutineScope {
            try {
                val articlesDeferred = async { api.retrofitService.getArticles().results }
                val articles = articlesDeferred.await()

                _articleResult.postValue(articles)

                for ((index, article) in articles.withIndex()) {
                    val categories = article.categories.joinToString(", ") { "${it.name}(${it.id})" }
                    Log.d("ARTICLE", "${index + 1}. ${article.title}: ${article.publishDate}, CATEGORIES: $categories")
                }

            } catch (e: Exception) {
                Log.e("ARTICLE LOADING ERROR", "Error fetching article results: ${e.message}")
            }
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getLatestNews() {
        coroutineScope {
            try {
                val articlesDeferred = async { api.retrofitService.getLatestNews().results }
                val articles = articlesDeferred.await()

                _articleLatestNews.postValue(articles)

                for ((index, article) in articles.withIndex()) {
                    val categories = article.categories.joinToString(", ") { "${it.name}(${it.id})" }
                    Log.d("ARTICLE", "${index + 1}. ${article.title}: ${article.publishDate}, CATEGORIES: $categories")
                }

            } catch (e: Exception) {
                Log.e("ARTICLE LOADING ERROR", "Error fetching article results: ${e.message}")
            }
        }
    }













    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getArticlesByCategories(categoryIds: List<Int>) {
        coroutineScope {
            try {
                val articlesDeferred = async { api.retrofitService.getArticles().results }
                val articles = articlesDeferred.await()

                val filteredArticles = articles.filter { article ->
                    article.categories.any { category -> categoryIds.contains(category.id) }
                }

                _articleResult.postValue(filteredArticles)

                for ((index, article) in filteredArticles.withIndex()) {
                    val categories = article.categories.joinToString(", ") { "${it.name}(${it.id})" }
                    Log.d("ARTICLE", "${index + 1}. ${article.title}: ${article.publishDate}, CATEGORIES: $categories")
                }

            } catch (e: Exception) {
                Log.e("ARTICLE LOADING ERROR", "Error fetching article results: ${e.message}")
            }
        }
    }







}




/*
suspend fun getArticles() {
    coroutineScope {
        try {

            val startTime = System.currentTimeMillis()
            val articlesDeferred = async { api.retrofitService.getArticles().results }
            val articles = articlesDeferred.await()

            val endTime = System.currentTimeMillis()
            val duration = (endTime - startTime) / 1000.0

            Log.d("ARTICLE LOADING TIME", "LOADING TIME: $duration seconds")

            _articleResult.postValue(articles)

            */
/*for ((index, article) in articles.withIndex()) {
                val categories = article.categories.joinToString(", ") { "${it.name}(${it.id})" }
                Log.d("ARTICLE", "${index + 1}. ${article.title}: ${article.publishDate}, CATEGORIES: $categories")
            }*//*


        } catch (e: Exception) {
            Log.e("ARTICLE LOADING ERROR", "Error fetching article results: ${e.message}")
        }
    }
}*/
