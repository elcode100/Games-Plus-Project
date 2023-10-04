package com.example.games_plus.data

import android.util.Log
import com.example.games_plus.data.model.Game
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/*val db = FirebaseFirestore.getInstance()

suspend fun getAllGames() {
    try {

        val specificGamesFromFirestore = getGamesFromFirestore("specificGames")
        val specificGames = if (specificGamesFromFirestore.isEmpty()) {
            val gamesFromApi = api.retrofitService.getSpecificGames().results
            saveGamesToFirestore("specificGames", gamesFromApi)
            gamesFromApi
        } else {
            specificGamesFromFirestore
        }


        val recentGamesFromFirestore = getGamesFromFirestore("recentGames")
        val recentGames = if (recentGamesFromFirestore.isEmpty()) {
            val gamesFromApi = api.retrofitService.getRecentGames().results.map { game ->
                val genreResponse = api.retrofitService.getGameGenres(game.guid)
                game.genres = genreResponse.results.genres
                game
            }
            saveGamesToFirestore("recentGames", gamesFromApi)
            gamesFromApi
        } else {
            recentGamesFromFirestore
        }

        for (game in specificGames) {
            val genreResponse = api.retrofitService.getGameGenres(game.guid)
            game.genres = genreResponse.results.genres
        }

        val rolePlayingGames = (specificGames + recentGames).filter { game ->
            game.genres?.any { genre -> genre.id == 5 } == true
        }

        _gameResult.postValue(rolePlayingGames)

        for ((index, game) in rolePlayingGames.withIndex()) {
            Log.d("GAME", "${index + 1}. ${game.name}")
        }

    } catch (e: Exception) {
        Log.e("GAME LOADING", "Error fetching game results: ${e.message}")
    }
}


fun saveGamesToFirestore(collectionName: String, games: List<Game>) {
    val gamesCollection = db.collection(collectionName)
    for (game in games) {
        gamesCollection.document(game.id.toString()).set(game)
    }
}


suspend fun getGamesFromFirestore(collectionName: String): List<Game> {
    val gamesFromFirestore = mutableListOf<Game>()
    val documents = db.collection(collectionName).get().await()
    for (document in documents) {
        gamesFromFirestore.add(document.toObject(Game::class.java))
    }
    return gamesFromFirestore
}*/







/*
suspend fun getAllGames() {
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







// GOOD !!!
/*suspend fun getAllGames() {
    try {

        val gamesFromApi = api.retrofitService.getRecentGames().results
        val favGames = api.retrofitService.getRecentGames(filter = favGames).results
        val allGames = favGames + gamesFromApi


        allGames.map { game ->
            val genreResponse = api.retrofitService.getGameGenres(game.guid)
            game.genres = genreResponse.results.genres

        }


        _gameResult.postValue(allGames)

        for ((index, game) in (allGames).withIndex()) {
            Log.d("BEST GAME", "${index + 1}. ${game.name}")
        }
    } catch (e: Exception) {
        Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")

    }
}*/

// TEST FIRESTORE --- GOOD BUT NOT IN REAL TIME


/*val db = FirebaseFirestore.getInstance()
suspend fun getAllGames() {
    try {
        val recentGamesFromFirestore = getGamesFromFirestore("allGames")
        val recentGames = if (recentGamesFromFirestore.isEmpty()) {
            val gamesFromApi = api.retrofitService.getRecentGames().results

            val favGames = api.retrofitService.getRecentGames(filter = favGames).results
            val allGames = favGames + gamesFromApi

            val updatedGames = allGames.map { game ->
                val genreResponse = api.retrofitService.getGameGenres(game.guid)
                game.copy(genres = genreResponse.results.genres)
            }

            saveGamesToFirestore("allGames", updatedGames)
            updatedGames
        } else {
            recentGamesFromFirestore
        }

        _gameResult.postValue(recentGames)

        for ((index, game) in recentGames.withIndex()) {
            Log.d("BEST GAME", "${index + 1}. ${game.name}")
        }

    } catch (e: Exception) {
        Log.e("GAME LOADING ERROR", "Error fetching game results: ${e.message}")
    }
}

fun saveGamesToFirestore(collectionName: String, games: List<Game>) {
    val batch = db.batch()
    val gamesCollection = db.collection(collectionName)
    for ((index, game) in games.withIndex()) {
        game.index = index
        val docRef = gamesCollection.document(game.id.toString())
        batch.set(docRef, game)
    }
    batch.commit()
}

suspend fun getGamesFromFirestore(collectionName: String): List<Game> {
    val gamesFromFirestore = mutableListOf<Game>()
    val documents = db.collection(collectionName).orderBy("index").get().await()
    for (document in documents) {
        gamesFromFirestore.add(document.toObject(Game::class.java))
    }
    return gamesFromFirestore
}*/
