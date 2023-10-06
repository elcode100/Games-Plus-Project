package com.example.games_plus.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.games_plus.data.api.GamesApi
import com.example.games_plus.data.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class Repository(private val api: GamesApi) {


    /*private val _gameResult = MutableLiveData<MutableList<Game>>(mutableListOf())
    val gameResult: LiveData<MutableList<Game>>
        get() = _gameResult*/

    private val _gameResult = MutableLiveData<List<Game>>()
    val gameResult: LiveData<List<Game>>
        get() = _gameResult


    /*private val _upcomingGameResult = MutableLiveData<MutableList<Game>>(mutableListOf())
    val upcomingGameResult: LiveData<MutableList<Game>>
        get() = _upcomingGameResult*/


    private val _upcomingGameResult = MutableLiveData<List<Game>>()
    val upcomingGameResult: LiveData<List<Game>>
        get() = _upcomingGameResult


    private val _mobileGameResult = MutableLiveData<List<Game>>()
    val mobileGameResult: LiveData<List<Game>>
        get() = _mobileGameResult


    private val _searchResult = MutableLiveData<List<Game>>()
    val searchResult: LiveData<List<Game>>
        get() = _searchResult



    val witcher3 = 41484
    val massEffectLegendary = 81128
    val gta5 = 36765
    val bioShockInfinite = 32317
    val cyberpunk2077 = 55760



    private val favGames = "id:$cyberpunk2077|$witcher3|$massEffectLegendary|$gta5|51351|38456|78695|52647|78967|$bioShockInfinite|80641|68449|61028"



    /*suspend fun getAllGames(){
        coroutineScope {
            var allGames : List<Game> = listOf()
            async {
                val gamesFromApi = api.retrofitService.getRecentGames().results
                val favGames = api.retrofitService.getRecentGames(filter = favGames).results

                allGames = favGames + gamesFromApi

                for ((index, game) in (allGames).withIndex()) {
                    Log.d("BEST GAMES", "${index + 1}. ${game.name}")
                }
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



    suspend fun getAllGames() {
        try {

            val gamesFromApi = api.retrofitService.getRecentGames().results
            val favGames = api.retrofitService.getRecentGames(filter = favGames).results
            val allGames = favGames + gamesFromApi


            /*allGames.map { game ->
                val genreResponse = api.retrofitService.getGameGenres(game.guid)
                game.genres = genreResponse.results.genres

            }*/


            _gameResult.postValue(allGames)

            for ((index, game) in (allGames).withIndex()) {
                Log.d("BEST GAME", "${index + 1}. ${game.name}")
            }
        } catch (e: Exception) {
            Log.e("BEST GAME LOADING ERROR", "Error fetching game results: ${e.message}")

        }
    }


    suspend fun loadGenresForAllGames(allGames: List<Game>) {
        allGames.map { game ->
            val genreResponse = api.retrofitService.getGameGenres(game.guid)
            game.genres = genreResponse.results.genres
        }
    }







    private val specUpcomingGames = "id:81572|32327" /*79858|*/


    /*@RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUpcomingGames() {
        coroutineScope {
            var allUpcomingGames: List<Game> = listOf()
            async {
                val gamesFromApi = api.retrofitService.getUpcomingGames().results
                val specUpcomingGames = api.retrofitService.getUpcomingGames(filter = specUpcomingGames).results
                allUpcomingGames = specUpcomingGames + gamesFromApi
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





     @RequiresApi(Build.VERSION_CODES.O)
     suspend fun getUpcomingGames() {


             try {
                 /*val gamesFromApi = api.retrofitService.getUpcomingGames().results
                 val specUpcomingGames = api.retrofitService.getUpcomingGames(filter = specUpcomingGames).results
                 val allUpcomingGames = specUpcomingGames + gamesFromApi*/

                 val upcomingGames = api.retrofitService.getUpcomingGames().results


                 /*for (game in allUpcomingGames) {
                     try {
                         val genreResponse = api.retrofitService.getGameGenres(game.guid)
                         game.genres = genreResponse.results.genres
                     } catch (e: Exception) {
                         game.genres = emptyList()
                     }

                 }*/

                 _upcomingGameResult.postValue(upcomingGames)

                 for ((index, game) in (upcomingGames).withIndex()) {
                     Log.d("UPCOMING GAME", "${index + 1}. ${game.name}")
                 }


             } catch (e: HttpException) {
                 if (e.code() == 420 || e.code() == 429) {
                     delay(5000)

                 } else {
                     Log.e("UPCOMING GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                     throw e
                 }
             } catch (e: Exception) {
                 Log.e("UPCOMING GAME LOADING ERROR", "Error fetching game results: ${e.message}")

                 delay(5000)
             }

     }





    private val specMobileGames = "id:67843|83987|70216"


    suspend fun getMobileGames() {


            try {
                val gamesFromApi = api.retrofitService.getMobileGames().results
                val specMobileGames = api.retrofitService.getMobileGames(filter = specMobileGames).results
                val allMobileGames = specMobileGames + gamesFromApi

                /*for (game in allMobileGames) {
                    try {
                        val genreResponse = api.retrofitService.getGameGenres(game.guid)
                        game.genres = genreResponse.results.genres
                    } catch (e: Exception) {
                        game.genres = emptyList()
                    }

                }*/

                _mobileGameResult.postValue(allMobileGames)

                for ((index, game) in (allMobileGames).withIndex()) {
                    Log.d("MOBILE GAME", "${index + 1}. ${game.name}")
                }


            } catch (e: HttpException) {
                if (e.code() == 420 || e.code() == 429) {
                    delay(5000)

                } else {
                    Log.e("MOBILE GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                    throw e
                }
            } catch (e: Exception) {
                Log.e("MOBILE GAME LOADING ERROR", "Error fetching game results: ${e.message}")

                delay(5000)
            }

    }


    /*suspend fun getMobileGames() {
        var retryCount = 5
        while (retryCount > 0) {
            try {
                val gamesFromApi = api.retrofitService.getMobileGames().results
                val specMobileGames = api.retrofitService.getMobileGames(filter = specMobileGames).results
                val allMobileGames = specMobileGames + gamesFromApi

                for (game in allMobileGames) {

                    val genreResponse = api.retrofitService.getGameGenres(game.guid)
                    game.genres = genreResponse.results.genres
                    _mobileGameResult.postValue(allMobileGames)
                }


                for ((index, game) in (allMobileGames).withIndex()) {
                    Log.d("MOBILE GAME", "${index + 1}. ${game.name}")
                }


                break
            } catch (e: HttpException) {
                if (e.code() == 420 || e.code() == 429) {
                    delay(5000)
                    retryCount--
                } else {
                    Log.e("MOBILE GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                    throw e
                }
            } catch (e: Exception) {
                Log.e("MOBILE GAME LOADING ERROR", "Error fetching game results: ${e.message}")
                retryCount--
                delay(5000)
            }
        }
    }*/





    suspend fun getSearchGameResult(query: String) {
        try {
            val searchResults = api.retrofitService.getSearchGameResult(query = query)
            _searchResult.postValue(searchResults.results)
        } catch (e: Exception) {
            Log.e("SearchResponse", "${e.message}")
        }
    }







}













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
               val specificGames = api.retrofitService.getUpcomingGames(filter = specificUpcomingGames).results // Umbenannt
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