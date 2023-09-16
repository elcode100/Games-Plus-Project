package com.example.games_plus.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.games_plus.data.Repository
import com.example.games_plus.data.model.Game
import com.example.games_plus.data.remote.GamesApi
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


const val TAG = "MainViewModel"


class MainViewModel: ViewModel() {


    private val repository = Repository(GamesApi)
    val dataList = repository.gameResult
    private val firebaseStore = FirebaseFirestore.getInstance()


    private val _currentResult = MutableLiveData<Game>()
    val currentResult: LiveData<Game>
        get() = _currentResult

    private val _favoriteGames = MutableLiveData<List<Game>>()
    val favoriteGames: LiveData<List<Game>>
        get() = _favoriteGames




    fun loadAllGames() {
        viewModelScope.launch {

            try {
                repository.getAllGames()
            } catch (e: Exception) {
                Log.e(TAG, "Error loading game data: $e")
            }
        }
    }



    fun assignYouTubeIdsToGame(game: Game): Game {
        return when (game.id) {
            41484 -> game.copy(youtubeId = listOf("c0i88t0Kacs"))
            36765 -> game.copy(youtubeId = listOf("hvoD7ehZPcM"))
            81128 -> game.copy(youtubeId = listOf("n8i53TtQ6IQ"))
            20538 -> game.copy(youtubeId = listOf("1_Q3MZC-34o"))
            else -> game
        }
    }




    fun updateResult(result: Game) {
        _currentResult.value = result
    }




    fun addToFavorites(result: Game) {
        val currentFavorites = _favoriteGames.value?.toMutableList() ?: mutableListOf()
        if (!currentFavorites.contains(result)) {
            currentFavorites.add(result)
            _favoriteGames.value = currentFavorites


            firebaseStore.collection("favoriteGames")
                .document(result.id.toString())
                .set(result)
        }
    }

    fun removeFromFavorites(result: Game) {
        val currentFavorites = _favoriteGames.value?.toMutableList() ?: mutableListOf()
        currentFavorites.remove(result)
        _favoriteGames.value = currentFavorites


        firebaseStore.collection("favoriteGames")
            .document(result.id.toString())
            .delete()
    }




    fun isGameFavored(gameId: Int): Boolean {
        return _favoriteGames.value?.find { it.id == gameId } != null
    }




    fun loadFavoriteGames() {
        firebaseStore.collection("favoriteGames")
            .get()
            .addOnSuccessListener { documents ->
                val favorites = documents.map { it.toObject(Game::class.java) }
                _favoriteGames.value = favorites
            }
    }




}
