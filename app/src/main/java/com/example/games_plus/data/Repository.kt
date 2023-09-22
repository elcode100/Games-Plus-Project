package com.example.games_plus.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.games_plus.data.remote.GamesApi
import com.example.games_plus.data.model.Game

class Repository(private val api: GamesApi) {

    private val _gameResult = MutableLiveData<List<Game>>()
    val gameResult: LiveData<List<Game>>
        get() = _gameResult

    private val _searchResult = MutableLiveData<List<Game>>()
    val searchResult: LiveData<List<Game>>
        get() = _searchResult

    private val _gameGenres = MutableLiveData<List<Game>>()
    val gameGenres: LiveData<List<Game>>
        get() = _gameGenres





    suspend fun getAllGames() {
        try {
            val specificGames = api.retrofitService.getSpecificGames().results
            val recentGames = api.retrofitService.getRecentGames().results
            for (game in specificGames) {

                val genreResponse = api.retrofitService.getGameGenres(game.guid)
                game.genres = genreResponse.results.genres


            }

            _gameResult.postValue(specificGames + recentGames)
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching game results: ${e.message}")
        }
    }



    suspend fun getSearchGameResult(query: String) {
        try {
            val searchResults = api.retrofitService.getSearchGameResult(query = query)
            _searchResult.postValue(searchResults.results)
        } catch (e: Exception) {
            Log.e("SearchResponse", "${e.message}")
        }
    }



    /*suspend fun getGameGenres(guid: String) {
        try {
            val genreResults = api.retrofitService.getGameGenres(guid = guid)
            _gameGenres.postValue(genreResults.results)
        } catch (e: Exception) {
            Log.e("GenreResponse", "${e.message}")
        }
    }*/




}












/*
suspend fun getGameResults() {
    try {
        _gameResult.postValue(api.retrofitService.getGameResults().results)
    } catch (e: Exception) {
        Log.e("Repository", "Error fetching game results: ${e.message}")
    }
}*/
