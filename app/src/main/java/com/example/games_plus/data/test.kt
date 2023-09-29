package com.example.games_plus.data

import android.util.Log
import com.example.games_plus.data.model.Game

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
