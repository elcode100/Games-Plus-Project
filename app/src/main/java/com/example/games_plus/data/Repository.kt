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





    suspend fun getAllGames() {
        try {
            val specificGames = api.retrofitService.getSpecificGames().results
            val recentGames = api.retrofitService.getRecentGames().results
            _gameResult.postValue(specificGames + recentGames)
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching game results: ${e.message}")
        }
    }



}












/*
suspend fun getGameResults() {
    try {
        _gameResult.postValue(api.retrofitService.getGameResults().results)
    } catch (e: Exception) {
        Log.e("Repository", "Error fetching game results: ${e.message}")
    }
}*/
