package com.example.games_plus.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.games_plus.data.api.GamesApi
import com.example.games_plus.data.models.developers.Developer
import com.example.games_plus.data.models.games.Game
import com.example.games_plus.data.models.genres.Genre
import com.example.games_plus.data.models.reviews.UserReview
import com.example.games_plus.data.models.videos.VideoDetail
import kotlin.math.round
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.math.pow

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


    private val _videoResult = MutableLiveData<List<VideoDetail>>()
    val videoResult: LiveData<List<VideoDetail>>
        get() = _videoResult


    private val videoDetailsMap: MutableMap<String, List<VideoDetail>> = mutableMapOf()
    private val loadedGenresMap = mutableMapOf<String, List<Genre>>()
    private val loadedDevelopersMap = mutableMapOf<String, List<Developer>>()





    private val gameIds = mapOf(
        "Frostpunk" to 55760,
        "The Witcher 3: Wild Hunt" to 41484,
        "Mass Effect Legendary Edition" to 81128,
        "GTA 5" to 36765,
        "BioShock Infinite" to 32317,
        "Cyberpunk 2077" to 38456,
        "Destiny 2" to 52647,
        "Detroit: Become Human" to 51351,
        "Anno 1800" to 61028,
        "Atomic Heart" to 68449,
        "Mafia: Definitive Edition" to 78695,
        "Resident Evil Village" to 78967,
        "Hogwarts Legacy" to 80641,
        "Red Dead Redemption 2" to 56725,
        "Elden Ring" to 73745,
        "Batman: Arkham City" to 29443,
        "Mafia 2" to 20538,
        "Marvel's Spider-Man" to 54233,
        "Resident Evil 4 (2023) " to 86329,
        "Call of Duty: Modern Warfare 2" to 85447,
        "Gran Turismo 7" to 78951,
        "Assassin's Creed Origins" to 59450,
        "Tom Clancy's Rainbow Six Siege" to 46562,
        "Gof of War (2018)" to 54229


    )

    val favGames = "id:${gameIds.values.joinToString("|")}"




/*
    suspend fun getAllGames() {
        coroutineScope {
            var retryCount = 0
            val maxRetries = 3

            while (retryCount < maxRetries) {
                try {
                    val gamesDeferred = async { api.retrofitService.getRecentGames().results }
                    val favGamesDeferred = async { api.retrofitService.getRecentGames(filter = favGames).results }

                    val gamesFromApi = gamesDeferred.await()
                    val favGames = favGamesDeferred.await()
                    val allGames = favGames + gamesFromApi

                    for ((index, game) in allGames.withIndex()) {
                        Log.d("BEST GAME", "${index + 1}. ${game.name}")
                    }

                    _gameResult.postValue(allGames)
                    break

                } catch (e: HttpException) {
                    if (e.code() == 502) {
                        retryCount++
                        delay(3000)
                    } else {
                        Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                        break
                    }
                } catch (e: SocketTimeoutException) {
                    retryCount++
                    Log.e("NETWORK TIMEOUT ERROR", "Network Timeout, retrying... ${e.message}")
                    delay(3000)

                } catch (e: Exception) {
                    Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                    break
                }
            }
        }
    }
*/




    @SuppressLint("SuspiciousIndentation")
    /*suspend fun getAllGames() {
        coroutineScope {
            var retryCount = 0
            val maxRetries = 3

            while (retryCount < maxRetries) {
                try {
                    val gamesDeferred = async { api.retrofitService.getRecentGames().results }
                    val favGamesDeferred = async { api.retrofitService.getRecentGames(filter = favGames).results }

                    val gamesFromApi = gamesDeferred.await()
                    val favGames = favGamesDeferred.await()
                    val allGames = favGames + gamesFromApi


                    *//*for ((index, game) in allGames.withIndex()) {
                        Log.d("BEST GAME", "${index + 1}. ${game.name}")
                    }*//*

                 val gamesWithScore = allGames.map { game ->
                        async {
                            val reviews = getUserReviews(game.guid)
                            game.averageUserScore = calculateAverageScore(reviews)
                            return@async game
                        }

                    }.awaitAll()

                    _gameResult.postValue(gamesWithScore)
                    break

                } catch (e: HttpException) {
                    if (e.code() == 420 || e.code() == 502) {
                        retryCount++
                        delay((retryCount * 1000).toLong())
                    } else {
                        Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                        break
                    }
                } catch (e: SocketTimeoutException) {
                    retryCount++
                    Log.e("NETWORK TIMEOUT ERROR", "Network Timeout, retrying... ${e.message}")
                    delay(3000)

                } catch (e: Exception) {
                    Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                    break
                }
            }
        }
    }*/

    suspend fun getAllGames() {
        coroutineScope {
            var retryCount = 0
            val maxRetries = 3

            while (retryCount < maxRetries) {
                try {

                    val bestGamesDeferred = async { api.retrofitService.getRecentGames(filter = favGames).results }


                    val bestGames = bestGamesDeferred.await()


                    for ((index, game) in bestGames.withIndex()) {
                        Log.d("BEST GAME", "${index + 1}. ${game.name}")
                    }


                    bestGames.take(10).forEach { game ->
                        val reviews = getUserReviews(game.guid)
                        game.averageUserScore = calculateAverageScore(reviews)
                    }


                    launch {
                        bestGames.drop(10).forEach { game ->
                            val reviews = getUserReviews(game.guid)
                            game.averageUserScore = calculateAverageScore(reviews)
                        }
                    }

                    _gameResult.postValue(bestGames)
                    break

                } catch (e: HttpException) {
                    if (e.code() == 420 || e.code() == 502) {
                        retryCount++
                        delay((retryCount * 1000).toLong())
                    } else {
                        Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                        break
                    }
                } catch (e: SocketTimeoutException) {
                    retryCount++
                    Log.e("NETWORK TIMEOUT ERROR", "Network Timeout, retrying... ${e.message}")
                    delay(3000)
                } catch (e: Exception) {
                    Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                    break
                }
            }
        }
    }





    /*suspend fun getUserReviews(gameGuid: String): List<UserReview> {
        val game = "game:$gameGuid"
        return api.retrofitService.getUserReviews(filter = game).results
    }
*/



    private val loadedReviewsMap = mutableMapOf<String, List<UserReview>>()

    suspend fun getUserReviews(gameGuid: String): List<UserReview> {
        return coroutineScope {

            loadedReviewsMap[gameGuid]?.let { return@coroutineScope it }

            val maxRetries = 3
            val baseDelayMillis = 1000L

            for (attempt in 1..maxRetries) {
                try {
                    val game = "game:$gameGuid"
                    val reviews = api.retrofitService.getUserReviews(filter = game).results


                    loadedReviewsMap[gameGuid] = reviews

                    return@coroutineScope reviews

                } catch (e: SocketTimeoutException) {
                    Log.e("NETWORK TIMEOUT ERROR", "Network Timeout fetching user reviews: ${e.message}")

                    if (attempt < maxRetries) {
                        val delayMillis = baseDelayMillis * (2.0.pow(attempt - 1)).toLong()
                        delay(delayMillis)
                    } else {
                        throw e
                    }

                } catch (e: HttpException) {
                    Log.e("USER REVIEW ERROR", "Error fetching user reviews: ${e.message}")
                    throw e
                } catch (e: Exception) {
                    Log.e("USER REVIEW ERROR", "Unexpected error fetching user reviews: ${e.message}")
                    throw e
                }
            }


            return@coroutineScope listOf()
        }
    }










    fun calculateAverageScore(reviews: List<UserReview>): Double {
        val totalScore = reviews.sumBy { it.score ?: 0 }
        val averageScore = totalScore.toDouble() / reviews.size


        return round(averageScore * 10) / 10
    }











    /*fun calculateAverageScore(reviews: List<UserReview>): Double {
        val totalScore = reviews.fold(0) { sum, review -> sum + (review.score ?: 0) }
        return totalScore.toDouble() / reviews.size
    }*/




    suspend fun loadGenresForGame(game: Game): Game {
        return coroutineScope {
            val gameId = game.id.toString()

            if (loadedGenresMap.containsKey(gameId)) {

                game.genres = loadedGenresMap[gameId] ?: emptyList()
            } else {
                try {
                    val genreResponse = api.retrofitService.getGameGenres(game.guid)
                    game.genres = genreResponse.results.genres

                    game.genres?.let { loadedGenresMap[gameId] = it }
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Error Loading Genre: ${e.message}", e)
                    game.genres = emptyList()
                }
            }

            game
        }
    }


    suspend fun loadDevelopersForGame(game: Game): Game {
        return coroutineScope {
            val gameId = game.id.toString()

            if (loadedDevelopersMap.containsKey(gameId)) {

                game.developers = loadedDevelopersMap[gameId] ?: emptyList()
            } else {
                try {
                    val developerResponse = api.retrofitService.getGameDevelopers(game.guid)
                    game.developers = developerResponse.results.developers

                    game.developers?.let { loadedDevelopersMap[gameId] = it }
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Error Loading Developer: ${e.message}", e)
                    game.developers = emptyList()
                }
            }

            game
        }
    }





    private suspend fun loadVideosForGame(guid: String) {
        try {
            val videoResponse = api.retrofitService.getGameVideos(guid)

            val videos = videoResponse.results.videos
            val videoDetailList = mutableListOf<VideoDetail>()

            for (video in videos) {
                val splitString = video.apiDetailUrl.split("/")


                if (splitString.size > 5) {
                    try {
                        val videoGuid = splitString[5]
                        Log.e("VIDEO GUID ", videoGuid)
                        val detailResponse = api.retrofitService.getVideoDetails(videoGuid)
                        videoDetailList.add(detailResponse.results)


                    } catch (e: Exception) {

                        Log.e(ContentValues.TAG, "Loading video details: ${e.message}")
                    }


                }


            }
            videoDetailsMap[guid] = videoDetailList
            _videoResult.postValue(videoDetailList)
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error loading videos for game: ${e.message}", e)
        }
    }


    suspend fun getVideoDetails(guid: String) {

        _videoResult.value = listOf()

        if (videoDetailsMap.keys.contains(guid)) {

            _videoResult.value = videoDetailsMap[guid]


        } else {

            loadVideosForGame(guid)

        }


    }






    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUpcomingGames() {
        coroutineScope {
            try {
                val upcomingGamesDeferred = async { api.retrofitService.getUpcomingGames().results }
                val upcomingGames = upcomingGamesDeferred.await()

                val filteredGames = upcomingGames.filter { game ->
                    game.platforms?.all { platform ->
                        platform.id in listOf(94, 145, 146, 176, 179)
                    } == true
                }

                _upcomingGameResult.postValue(filteredGames)

                for ((index, game) in filteredGames.withIndex()) {
                    Log.d("UPCOMING GAME", "${index + 1}. ${game.name}")
                }
            } catch (e: Exception) {
                Log.e("UPCOMING GAME LOADING ERROR", "Error fetching game results: ${e.message}")
            }
        }
    }


    /*@RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUpcomingGames() {
        coroutineScope {
            try {
                var offset = 0
                val limit = 50
                val filteredGames = mutableListOf<Game>()

                while (filteredGames.size < 50) {
                    val upcomingGamesDeferred = async {
                        api.retrofitService.getUpcomingGames(offset = offset, limit = limit).results
                    }
                    val upcomingGames = upcomingGamesDeferred.await()

                    val validGames = upcomingGames.filter { game ->
                        game.platforms?.all { platform ->
                            platform.id in listOf(94, 145, 146, 176, 179)
                        } == true
                    }

                    filteredGames.addAll(validGames)
                    offset += limit
                }

                _upcomingGameResult.postValue(filteredGames.take(50))

                for ((index, game) in filteredGames.withIndex()) {
                    Log.d("UPCOMING GAME", "${index + 1}. ${game.name}")
                }
            } catch (e: Exception) {
                Log.e("UPCOMING GAME LOADING ERROR", "Error fetching game results: ${e.message}")
            }
        }
    }*/






    private val specMobileGames = "id:67843|83987|70216"



    suspend fun getMobileGames() {
        coroutineScope {
            try {
                val gamesFromApiDeferred = async { api.retrofitService.getMobileGames().results }
                val specMobileGamesDeferred = async { api.retrofitService.getMobileGames(filter = specMobileGames).results }

                val gamesFromApi = gamesFromApiDeferred.await()
                val specMobileGames = specMobileGamesDeferred.await()
                val allMobileGames = specMobileGames + gamesFromApi

                _mobileGameResult.postValue(allMobileGames)

                /*for ((index, game) in allMobileGames.withIndex()) {
                    Log.d("MOBILE GAME", "${index + 1}. ${game.name}")
                }*/
            } catch (e: Exception) {
                Log.e("MOBILE GAME LOADING ERROR", "Error fetching game results: ${e.message}")
            }
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






/*suspend fun loadGenresForGame(game: Game): Game {
       return coroutineScope {
           try {
               val genreResponseDeferred = async { api.retrofitService.getGameGenres(game.guid) }
               val genreResponse = genreResponseDeferred.await()
               game.genres = genreResponse.results.genres
               game
           } catch (e: Exception) {
               Log.e(TAG, "Error Loading Genre: ${e.message}", e)
               game.genres = emptyList()
               game
           }
       }
   }*/


 // IMPORTANT VERY !
/*suspend fun getAllGames() {
      coroutineScope {
          try {
              val gamesDeferred = async { api.retrofitService.getRecentGames().results }
              val favGamesDeferred = async { api.retrofitService.getRecentGames(filter = favGames).results }

              val gamesFromApi = gamesDeferred.await()
              val favGames = favGamesDeferred.await()
              val allGames = favGames + gamesFromApi


              for ((index, game) in allGames.withIndex()) {
                  Log.d("BEST GAME", "${index + 1}. ${game.name}")
              }


              _gameResult.postValue(allGames)


          } catch (e: SocketTimeoutException) {
              Log.e("NETWORK TIMEOUT ERROR", "Network Timeout: ${e.message}")

          } catch (e: JsonEncodingException) {
              Log.e("JSON PARSING ERROR", "Error parsing JSON: ${e.message}")

          } catch (e: Exception) {
              Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")
          }
      }
  }*/










/*    suspend fun loadVideosForGame(guid: String): List<VideoDetail> {
        val videoDetailsList = mutableListOf<VideoDetail>()
        try {
            val videoResponse = api.retrofitService.getGameVideos(guid)
            videoResponse.results.videos.forEach { video ->
                val videoDetailResponse = api.retrofitService.getVideoDetails(video.apiDetailUrl)
                videoDetailsList.add(videoDetailResponse.results)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading videos for game: ${e.message}", e)
        }
        return videoDetailsList
    }*/





/*private val _genreResult = MutableLiveData<List<Genre>>()
val genreResult: LiveData<List<Genre>>
    get() = _genreResult


suspend fun loadGenreForGame(guid: String) {
    try {
        val genreResponse = api.retrofitService.getGameGenres(guid)
        _genreResult.postValue(genreResponse.results.genres)
    } catch (e: Exception) {
        Log.e(TAG, "Error loading videos for game: ${e.message}", e)
    }
}*/








// ETALON 2
/*suspend fun loadVideosForGame(guid: String): List<VideoDetail> {
    val videoDetailsList = mutableListOf<VideoDetail>()
    try {
        val videoResponse = api.retrofitService.getGameVideos(guid)
        videoResponse.results.videos.forEach { video ->
            val videoDetailUrl = "${video.apiDetailUrl}?api_key=$API_KEY&format=json"
            val videoDetailResponse = loadVideoDetails(videoDetailUrl)
            videoDetailResponse.videoDetail?.let {
                videoDetailsList.add(it)
            }
            videoDetailResponse.videoDetails?.let {
                videoDetailsList.addAll(it)
            }
        }
    } catch (e: Exception) {
        Log.e(TAG, "Error loading videos for game: ${e.message}", e)
    }
    return videoDetailsList
}

suspend fun loadVideoDetails(apiDetailUrl: String): VideoDetailResponse {
    return api.retrofitService.getVideoDetails(apiDetailUrl)
}*/

// Maybe

/*sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: Throwable) : Result<Nothing>()
}
val limit = 10

suspend fun loadVideosForGame(guid: String): List<VideoDetail> = coroutineScope {
    val videoDetailsList = mutableListOf<VideoDetail>()
    try {
        val videoResponse = api.retrofitService.getGameVideos(guid)
        val deferredList = videoResponse.results.videos.map { video ->
            async {
                val videoDetailUrl = "${video.apiDetailUrl}?api_key=$API_KEY&format=json&$limit"
                loadVideoDetails(videoDetailUrl)
            }
        }
        deferredList.forEach { deferred ->
            when (val result = deferred.await()) {
                is Result.Success -> {
                    result.data.videoDetail?.let {
                        videoDetailsList.add(it)
                    }
                    result.data.videoDetails?.let {
                        videoDetailsList.addAll(it)
                    }
                }
                is Result.Failure -> {
                    Log.e(TAG, "Error loading video details: ${result.error.message}", result.error)
                }
            }
        }
    } catch (e: Exception) {
        Log.e(TAG, "Error loading videos for game: ${e.message}", e)
    }
    return@coroutineScope videoDetailsList
}

suspend fun loadVideoDetails(apiDetailUrl: String): Result<VideoDetailResponse> {


        try {
            val response = api.retrofitService.getVideoDetails(apiDetailUrl)
            return Result.Success(response)
        } catch (e: HttpException) {
            if (e.code() == 420) {
                delay(1000)

            } else {
                return Result.Failure(e)
            }
        } catch (e: Exception) {
            return Result.Failure(e)
        }

    return Result.Failure(Exception("Failed to load video details after multiple attempts"))
}*/















/*suspend fun loadVideosForGame(guid: String) {
        try {
            val videoResponse = api.retrofitService.getGameVideos(guid)
            _videoResult.postValue(videoResponse.results.videos)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading videos for game: ${e.message}", e)
        }
    }*/



/*suspend fun loadVideosForGame(game: Game): Game {
       return coroutineScope {
           try {
               val videoResponseDeferred = async { api.retrofitService.getGameVideos(game.guid) }
               val videoResponse = videoResponseDeferred.await()
               game.videos = videoResponse.results.videos
               game
           } catch (e: Exception) {
               Log.e(TAG, "Error Loading Videos: ${e.message}", e)
               game.videos = emptyList()
               game
           }
       }
   }
*/







/*suspend fun loadGenresForGame(game: Game): Game {
        return try {
            val genreResponse = api.retrofitService.getGameGenres(game.guid)
            game.genres = genreResponse.results.genres
            game
        } catch (e: Exception) {
            Log.e(TAG, "Error Loading Genre: ${e.message}", e)
            game.genres = emptyList()
            game
        }
    }*/


/*suspend fun loadGenresForGame(game: Game): Game {
    return withContext(Dispatchers.IO) {
        try {
            val genreResponse = api.retrofitService.getGameGenres(game.guid)
            game.genres = genreResponse.results.genres
            game
        } catch (e: Exception) {

            Log.e(TAG, "Error Loading Genre: ${e.message}", e)
            game.genres = emptyList()
            game

        }
    }
}*/







/*allGames.map {
    game ->
    val genreResponse = api.retrofitService.getGameGenres(game.guid)
    game.genres = genreResponse.results.genres

}*/





/*private val _gameResult = MutableLiveData<List<Game>>()
   val gameResult: LiveData<List<Game>>
       get() = _gameResult

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
}*/



/*private val _upcomingGameResult = MutableLiveData<List<Game>>()
    val upcomingGameResult: LiveData<List<Game>>
        get() = _upcomingGameResult*/


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













/*suspend fun getAllGames(){

    val allGames : List<Game>

    val gamesFromApi = api.retrofitService.getRecentGames().results
    val favGames = api.retrofitService.getRecentGames(filter = favGames).results
    allGames = favGames + gamesFromApi

    getGenreDataForGameList(allGames)

}

suspend fun getGenreDataForGameList(gameList : List<Game>) {

    gameList.map {

        val game = getGenreDataForGame(it)
        _gameResult.value?.add(game)
        _gameResult.postValue(_gameResult.value)


    }

}

suspend fun getGenreDataForGame(game: Game) : Game{
    game.genres = api.retrofitService.getGameGenres(game.guid).results.genres
    return game
}*/






/*@RequiresApi(Build.VERSION_CODES.O)
   suspend fun getUpcomingGames() {
       coroutineScope {
           var allUpcomingGames: List<Game> = listOf()
           async {
               val gamesFromApi = api.retrofitService.getUpcomingGames().results
               val specificGames = api.retrofitService.getUpcomingGames(filter = specificUpcomingGames).results
               allUpcomingGames = specificGames + gamesFromApi
           }.await()
           getGenreDataForUpcomingGames(allUpcomingGames)
       }
   }



   suspend fun getGenreDataForUpcomingGames(gameList: List<Game>) {
       coroutineScope {
           gameList.map {
               async {
                   val game = try {
                       getGenreDataForUpcomingGame(it)
                   } catch (e: JsonDataException) {
                       it
                   }
                   withContext(Dispatchers.Main) {
                       _upcomingGameResult.value?.add(game)
                       _upcomingGameResult.postValue(_upcomingGameResult.value)
                   }
               }
           }.awaitAll()
       }
   }

   suspend fun getGenreDataForUpcomingGame(game: Game): Game {
       game.genres = api.retrofitService.getGameGenres(game.guid).results.genres
       return game
   }*/




/*private val replaceUpcomingGamesIds = mapOf(

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


            for ((index, game) in (upcomingGames).withIndex()) {
                Log.d("UPCOMING GAME", "${index + 1}. ${game.name}")
            }

            _upcomingGameResult.postValue(upcomingGames)

        } catch (e: Exception) {
            Log.e("UPCOMING GAME LOADING ERROR", "Error fetching game results: ${e.message}")
        }
    }


    private val replaceMobileGamesIds = mapOf(

        73245 to 67843,
        34621 to 83987,
        29030 to 70216,



        )


    suspend fun getMobileGames() {
        try {
            val mobileGames = api.retrofitService.getMobileGames().results.map{ game ->

                if (game.id in replaceMobileGamesIds.keys) {

                    api.retrofitService.getMobileGames(filter = "id:${replaceMobileGamesIds[game.id]}").results.first()

                } else {

                    game
                }

            }



            for ((index, game) in (mobileGames).withIndex()) {
                Log.d("MOBILE GAME", "${index + 1}. ${game.name}")
            }

            _mobileGameResult.postValue(mobileGames)

        } catch (e: Exception) {
            Log.e("MOBILE GAME LOADING ERROR", "Error fetching game results: ${e.message}")
        }
    }*/





// ETALON TILL


/*private val _gameResult = MutableLiveData<MutableList<Game>>(mutableListOf())
    val gameResult: LiveData<MutableList<Game>>
        get() = _gameResult*/

/*suspend fun getAllGames(){
        coroutineScope {
            var allGames : List<Game> = listOf()
            async {
                val gamesFromApi = api.retrofitService.getRecentGames().results
                val favGames = api.retrofitService.getRecentGames(filter = favGames).results

                allGames = favGames + gamesFromApi

                *//*for ((index, game) in (allGames).withIndex()) {
                    Log.d("BEST GAMES", "${index + 1}. ${game.name}")
                }*//*
            }.await()
            getGenreDataForGameList(allGames)
        }
    }

    suspend fun getGenreDataForGameList(gameList : List<Game>) {
        coroutineScope {
            gameList.map {
                async {
                    val game = getGenreDataForGame(it)
                    withContext(Dispatchers.Main){
                        _gameResult.value?.add(game)
                        _gameResult.postValue(_gameResult.value)

                    }
                }
            }.awaitAll()
        }
    }

    suspend fun getGenreDataForGame(game: Game) : Game{
        game.genres = api.retrofitService.getGameGenres(game.guid).results.genres
        return game
    }*/







//GENRES version 2

/*private val _genreResult = MutableLiveData<List<Genre>>()
val genreResult: LiveData<List<Genre>>
    get() = _genreResult

private val genresMap: MutableMap<String, List<Genre>> = mutableMapOf()


private suspend fun loadGenreForGame(guid: String) {
    try {
        val genreResponse = api.retrofitService.getGameGenres(guid)
        val genres = genreResponse.results.genres
        val genresList = mutableListOf<Genre>()

        for (genre in genres) {
            genresList.add(genre)
        }
        genresMap[guid] = genresList
        _genreResult.postValue(genresList)
    } catch (e: Exception) {
        Log.e(TAG, "Error loading videos for game: ${e.message}", e)
    }
}


suspend fun getGenres(guid: String) {
    _genreResult.value = listOf()

    if (genresMap.keys.contains(guid)) {

        _genreResult.value = genresMap[guid]

    } else {

        loadGenreForGame(guid)
    }
}*/
