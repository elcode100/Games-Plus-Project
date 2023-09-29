package com.example.games_plus.data

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.games_plus.data.remote.GamesApi
import com.example.games_plus.data.model.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Repository(private val api: GamesApi) {

    private val _gameResult = MutableLiveData<List<Game>>()
    val gameResult: LiveData<List<Game>>
        get() = _gameResult



    private val _upcomingGameResult = MutableLiveData<List<Game>>()
    val upcomingGameResult: LiveData<List<Game>>
        get() = _upcomingGameResult


    private val _mobileGameResult = MutableLiveData<List<Game>>()
    val mobileGameResult: LiveData<List<Game>>
        get() = _mobileGameResult


    private val _searchResult = MutableLiveData<List<Game>>()
    val searchResult: LiveData<List<Game>>
        get() = _searchResult




    /*// Mafia 2 to BioShock Infinite
        20538 to 32317,*/ /*36765*/


    val db = FirebaseFirestore.getInstance()
    private val replaceGameIds = mapOf(

         244 to 55760, // FrostPunk
         434 to 41484, // Witcher 3
        1385 to 81128, // ME Legendary Edition
        2161 to 36765, // GTA 5
        2415 to 51351, // Detroit: Become Human
        2540 to 38456, // Cyberpunk 2077
        2559 to 78695, // Mafia: Definitive Edition
        2797 to 52647, // Destiny 2
        2844 to 78967, // Resident Evil Village
        3573 to 32317, // BioShock Infinite
        3574 to 80641, // Hogwarts Legacy
        3913 to 68449, // Atomic Heart
        4426 to 61028, // Anno 1800





    )

  // ETALON

    suspend fun getAllGames() {
        try {

            val recentGamesFromFirestore = getGamesFromFirestore("recentGames")
            val recentGames = if (recentGamesFromFirestore.isEmpty()) {
                val gamesFromApi = api.retrofitService.getRecentGames().results.map { game ->
                    val genreResponse = api.retrofitService.getGameGenres(game.guid)
                    game.genres = genreResponse.results.genres
                    if (game.id in replaceGameIds.keys) {

                        val replacedGame = api.retrofitService.getRecentGames(filter = "id:${replaceGameIds[game.id]}").results.first()
                        val replacedGameGenreResponse = api.retrofitService.getGameGenres(replacedGame.guid)
                        replacedGame.genres = replacedGameGenreResponse.results.genres
                        replacedGame

                    } else {

                        game
                    }
                }
                saveGamesToFirestore("recentGames", gamesFromApi)
                gamesFromApi
            } else {
                recentGamesFromFirestore
            }


            _gameResult.postValue(recentGames)

            for ((index, game) in recentGames.withIndex()) {
                Log.d("GAME", "${index + 1}. ${game.name}")
            }

        } catch (e: Exception) {
            Log.e("GAME LOADING", "Error fetching game results: ${e.message}")
        }
    }


    fun saveGamesToFirestore(collectionName: String, games: List<Game>) {
        val gamesCollection = db.collection(collectionName)
        for ((index, game) in games.withIndex()) {
            game.index = index
            gamesCollection.document(game.id.toString()).set(game)
        }
    }



    suspend fun getGamesFromFirestore(collectionName: String): List<Game> {
        val gamesFromFirestore = mutableListOf<Game>()
        val documents = db.collection(collectionName).get().await()
        for (document in documents) {
            gamesFromFirestore.add(document.toObject(Game::class.java))
        }
        return gamesFromFirestore.sortedBy { it.index }
    }







    private val replaceUpcomingGamesIds = mapOf(

        89890 to 81572,
        86125 to 79858,
        87593 to 32327,



    )


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUpcomingGames() {
        try {
            val upcomingGames = api.retrofitService.getUpcomingGames().results.map{ game ->

                if (game.id in replaceUpcomingGamesIds.keys) {

                    api.retrofitService.getUpcomingGames(filter = "id:${replaceUpcomingGamesIds[game.id]}").results.first()

                } else {

                    game
                }

            }
            /*for (game in upcomingGames) {

                val genreResponse = api.retrofitService.getGameGenres(game.guid)
                game.genres = genreResponse.results.genres
            }*/


            for ((index, game) in (upcomingGames).withIndex()) {
                Log.d("UPCOMING GAME", "${index + 1}. ${game.name}")
            }

            _upcomingGameResult.postValue(upcomingGames)

        } catch (e: Exception) {
            Log.e("UPCOMING GAME LOADING ERROR", "Error fetching game results: ${e.message}")
        }
    }


    suspend fun getMobileGames() {
        try {
            _mobileGameResult.postValue(api.retrofitService.getMobileGames().results)

        } catch (e: Exception) {
            Log.e("UPCOMING GAME LOADING ERROR", "Error fetching mobile game results: ${e.message}")
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







}









/*@RequiresApi(Build.VERSION_CODES.O)
suspend fun getUpcomingGames() {
    try {

        val upcomingGamesFromFirestore = getGamesFromFirestore("upcomingGames")
        val upcomingGames = if (upcomingGamesFromFirestore.isEmpty()) {
            val gamesFromApi = api.retrofitService.getUpcomingGames().results
            saveGamesToFirestore("upcomingGames", gamesFromApi)
            gamesFromApi
        } else {
            upcomingGamesFromFirestore
        }

        for ((index, game) in upcomingGames.withIndex()) {
            Log.d("UPCOMING GAME", "${index + 1}. ${game.name}")
        }

        _upcomingGameResult.postValue(upcomingGames)

    } catch (e: Exception) {
        Log.e("UPCOMING GAME LOADING ERROR", "Error fetching game results: ${e.message}")
    }
}*/













/* private val replaceGameIds = mapOf(
        21840 to 56725,
        21810 to 38456,
        20538 to 32317,
        1385 to 55760,
        434 to 61028
    )


    suspend fun getAllGames() {
        try {
            val specificGames = api.retrofitService.getSpecificGames().results
            val recentGames = api.retrofitService.getRecentGames().results.map { game ->

                if (game.id in replaceGameIds.keys) {

                    api.retrofitService.getRecentGames(filter = "id:${replaceGameIds[game.id]}").results.first()

                } else {

                    game
                }

            }

            for (game in specificGames) {

                val genreResponse = api.retrofitService.getGameGenres(game.guid)
                game.genres = genreResponse.results.genres
            }

            _gameResult.postValue(specificGames + recentGames)

            for ((index, game) in (specificGames + recentGames).withIndex()) {
                Log.d("GAME", "${index + 1}. ${game.name}")
            }

        } catch (e: Exception) {
            Log.e("GAME LOADING", "Error fetching game results: ${e.message}")
        }
    }*/













/*suspend fun getAllGames() {
        try {

            val recentGames = api.retrofitService.getRecentGames().results.map { game ->

                if (game.id in replaceGameIds.keys) {

                    api.retrofitService.getRecentGames(filter = "id:${replaceGameIds[game.id]}").results.first()

                } else {

                    game
                }

            }

            for (game in recentGames) {

                val genreResponse = api.retrofitService.getGameGenres(game.guid)
                game.genres = genreResponse.results.genres
            }

            _gameResult.postValue(recentGames)

            for ((index, game) in (recentGames).withIndex()) {
                Log.d("GAME", "${index + 1}. ${game.name}")
            }

        } catch (e: Exception) {
            Log.e("GAME LOADING ERROR", "Error fetching game results: ${e.message}")
        }
    }*/



/*private val orderedGameIds = listOf(36765, 41484, 81128, 20538)
    val orderedSpecificGames = specificGames.sortedBy { orderedGameIds.indexOf(it.id) }*/


//Important !

/*private val excludedGameIds = listOf(21840)


suspend fun getAllGames() {
    try {
        val specificGames = api.retrofitService.getSpecificGames().results
        val recentGames = api.retrofitService.getRecentGames().results.filterNot { it.id in excludedGameIds }
        for (game in specificGames) {

            val genreResponse = api.retrofitService.getGameGenres(game.guid)
            game.genres = genreResponse.results.genres


        }

        _gameResult.postValue(specificGames + recentGames)

        for ((index, game) in (specificGames + recentGames).withIndex()) {
            Log.d("GAME", "${index + 1}. ${game.name}")
        }

    } catch (e: Exception) {
        Log.e("GAME LOADING", "Error fetching game results: ${e.message}")
    }
}*/












/*
suspend fun getGameResults() {
    try {
        _gameResult.postValue(api.retrofitService.getGameResults().results)
    } catch (e: Exception) {
        Log.e("Repository", "Error fetching game results: ${e.message}")
    }
}*/




















