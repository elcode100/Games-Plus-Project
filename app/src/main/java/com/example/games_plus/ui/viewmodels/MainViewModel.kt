package com.example.games_plus.ui.viewmodels

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.games_plus.data.Repository
import com.example.games_plus.data.model.Game
import com.example.games_plus.data.remote.GamesApi
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException


const val TAG = "MainViewModel"


class MainViewModel: ViewModel() {


    private val repository = Repository(GamesApi)
    val dataList = repository.gameResult
    val dataListUpcomingGames = repository.upcomingGameResult
    val dataListMobileGames = repository.mobileGameResult
    private val firebaseStore = FirebaseFirestore.getInstance()

    val searchList = repository.searchResult


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
                Log.e(TAG, "Error loading all games data: $e")
            }
        }
    }










    @RequiresApi(Build.VERSION_CODES.O)
    fun loadUpcomingGames() {
        viewModelScope.launch {

            try {
                repository.getUpcomingGames()

            } catch (e: Exception) {
                Log.e(TAG, "Error loading upcoming games data: $e")
            }
        }
    }

    fun loadMobileGames() {
        viewModelScope.launch {

            try {
                repository.getMobileGames()

            } catch (e: Exception) {
                Log.e(TAG, "Error loading mobile games data: $e")
            }
        }
    }


    fun searchGames(query: String) {
        viewModelScope.launch {
            try {
                repository.getSearchGameResult(query)
            } catch (e: Exception) {
                Log.e(TAG, "Error loading search results: $e")
            }
        }
    }







    fun assignYouTubeIdsToGame(game: Game): Game {
        return when (game.id) {
            41484 -> game.copy(youtubeId = listOf("c0i88t0Kacs"))
            36765 -> game.copy(youtubeId = listOf("hvoD7ehZPcM"))
            81128 -> game.copy(youtubeId = listOf("n8i53TtQ6IQ"))
            20538 -> game.copy(youtubeId = listOf("1_Q3MZC-34o"))
            32327 -> game.copy(youtubeId = listOf("SjDMwsbaSd8"))
            68449 -> game.copy(youtubeId = listOf("ZXSi4EcxnuY"))
            78695 -> game.copy(youtubeId = listOf("3PIWmbtcRgg"))
            42245 -> game.copy(youtubeId = listOf("9pnK8akbd2M"))
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



/*fun loadRolePlayingGamesFromFirebase() {
    viewModelScope.launch {
        try {
            repository.getAllGames()


            val rolePlayingGames = dataList.value?.filter { game ->
                game.genres?.any { genre -> genre.id == 5 } == true
            } ?: listOf()

            for ((index, game) in rolePlayingGames.withIndex()) {
                Log.d("GAME", "${index + 1}. ${game.name}")
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error loading game data from Firestore: $e")
        }
    }
}*/


/* init {
       repository.addFirestoreListener()
   }

   override fun onCleared() {
       super.onCleared()
       repository.removeFirestoreListener()
   }*/
