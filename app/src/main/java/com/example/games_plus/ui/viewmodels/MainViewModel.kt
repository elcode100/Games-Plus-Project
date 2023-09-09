package com.example.games_plus.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.games_plus.data.Repository
import com.example.games_plus.data.model.Response
import com.example.games_plus.data.model.Result
import com.example.games_plus.data.remote.GamesApi
import com.example.games_plus.data.remote.GamesApiService
import kotlinx.coroutines.launch
const val TAG = "MainViewModel"
class MainViewModel: ViewModel() {



    private val repository = Repository(GamesApi)
    val dataList= repository.gameResult

    private val _currentResult = MutableLiveData<Result>()
    val currentResult: LiveData<Result>
        get() = _currentResult





    fun fetchGames(size: Int) {
        viewModelScope.launch {
            try {

                repository.getGameResults(size)


            }catch (e: Exception) {

                Log.e(TAG,"ERROR LOADING DATA : $e")

            }

        }
    }





    fun updateResult(result: Result) {
        _currentResult.value = result
    }
}