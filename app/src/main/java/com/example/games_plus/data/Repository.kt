package com.example.games_plus.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.games_plus.data.remote.GamesApi
import com.example.games_plus.data.model.Result

class Repository(private val api: GamesApi) {



    private val _gameResult = MutableLiveData<List<Result>>()
    val gameResult: LiveData<List<Result>>
        get() = _gameResult

    suspend fun getGameResults(size: Int) {
        try {
            _gameResult.postValue(api.retrofitService.getGameResults(size).results)

        } catch (e: Exception) {

            Log.e("Repository", "${e.message}")
        }
    }

}
