package com.example.games_plus.ui.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.games_plus.R
import com.example.games_plus.data.Repository
import com.example.games_plus.data.RepositoryNews
import com.example.games_plus.data.api.GamesApi
import com.example.games_plus.data.api.NewsApi
import com.example.games_plus.data.models.News
import com.example.games_plus.data.models.games.Game
import com.example.games_plus.data.models.news.Article
import com.example.games_plus.data.models.videos.VideoDetail
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

const val TAG = "MainViewModel"


class MainViewModel: ViewModel() {

    private val firebaseStore = FirebaseFirestore.getInstance()

    private val repository = Repository(GamesApi)
    val dataList = repository.gameResult
    val dataListLast30DaysGames = repository.last30DaysGameResult
    val dataListUpcomingGames = repository.upcomingGameResult
    val dataListMobileGames = repository.mobileGameResult
    val searchList = repository.searchResult
    val dataListVideos = repository.videoResult


    private val _currentGame = MutableLiveData<Game>()
    val currentGame: LiveData<Game>
        get() = _currentGame

    private val _favoriteGames = MutableLiveData<List<Game>>()
    val favoriteGames: LiveData<List<Game>>
        get() = _favoriteGames



    private val _currentVideo = MutableLiveData<VideoDetail>()
    val currentVideo: LiveData<VideoDetail>
        get() = _currentVideo




    private val repositoryNews = RepositoryNews(NewsApi)
    val dataListNews = repositoryNews.articleResult
    val dataListLatestNews = repositoryNews.articleLatestNews


    private val _currentArticle = MutableLiveData<Article>()
    val currentArticle: LiveData<Article>
        get() = _currentArticle


    private val _currentLatestArticle = MutableLiveData<Article>()
    val currentLatestArticle: LiveData<Article>
        get() = _currentLatestArticle










    fun updateVideoData(video: VideoDetail) {
        _currentVideo.value = video
    }


    fun loadVideos() {

        viewModelScope.launch {

            try {
                _currentGame.value?.guid?.let { repository.getVideoDetails(it) }
            } catch (e: Exception) {

                Log.e(TAG, "ERROR LOADING VIDEOS FOR GAME")

            }

        }

    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun loadNews() {

        viewModelScope.launch {

            try {
                repositoryNews.getArticles()

            } catch (e: Exception) {
                Log.e(TAG, "Error loading news data: $e")
            }
        }


    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun loadLatestNews() {

        viewModelScope.launch {

            try {
                repositoryNews.getLatestNews()

            } catch (e: Exception) {
                Log.e(TAG, "Error loading news data: $e")
            }
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun loadNewsByCategories(categoryIds: List<Int>) {
        viewModelScope.launch {
            try {
                repositoryNews.getArticlesByCategories(categoryIds)
            } catch (e: Exception) {
                Log.e(TAG, "Error loading news data: $e")
            }
        }
    }



    fun updateNewsData(result: Article) {
        _currentArticle.value = result
    }







    fun loadBestGames() {
        viewModelScope.launch {

            try {
                repository.getBestGames()

            } catch (e: Exception) {
                Log.e(TAG, "Error loading all games data: $e")
            }
        }
    }





    @RequiresApi(Build.VERSION_CODES.O)
    fun loadLast30DaysGames() {
        viewModelScope.launch {

            try {
                repository.getLast30DaysGames()

            } catch (e: Exception) {
                Log.e(TAG, "Error loading upcoming games data: $e")
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
            41484 -> game.copy(youId = listOf("c0i88t0Kacs"))
            36765 -> game.copy(youId = listOf("hvoD7ehZPcM"))
            81128 -> game.copy(youId = listOf("n8i53TtQ6IQ"))
            20538 -> game.copy(youId = listOf("1_Q3MZC-34o"))
            32327 -> game.copy(youId = listOf("SjDMwsbaSd8"))
            68449 -> game.copy(youId = listOf("ZXSi4EcxnuY"))
            78695 -> game.copy(youId = listOf("3PIWmbtcRgg"))
            42245 -> game.copy(youId = listOf("9pnK8akbd2M"))
            else -> game
        }
    }




    fun updateResult(result: Game) {
        _currentGame.value = result
    }



    fun loadGenresForSelectedGame(game: Game) {
        viewModelScope.launch {
            try {
                val genresForGame = repository.loadGenresForGame(game)
                _currentGame.value = genresForGame
            } catch (e: Exception) {

                Log.e(TAG, "Error loading genre for selected game: ${e.message}", e)
            }
        }
    }



    fun loadDevelopersForSelectedGame(game: Game) {
        viewModelScope.launch {
            try {
                val developersForGame = repository.loadDevelopersForGame(game)
                _currentGame.value = developersForGame
            } catch (e: Exception) {

                Log.e(TAG, "Error loading developer for selected game: ${e.message}", e)
            }
        }
    }





    fun loadNewsImages() : List<News> {

        return listOf(
            News(R.drawable.cyberpunk_2077_phantom_liberty_placeholder),
            News(R.drawable.atomic_heart_dlc_placeholder),
            News(R.drawable.placeholder_starfield)



        )


    }




    /*fun addToFavorites(result: Game) {
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
*/

    fun toggleFavoriteStatus(game: Game) {
        val currentFavorites = _favoriteGames.value?.toMutableList() ?: mutableListOf()
        val friendIndex = currentFavorites.indexOfFirst { it.id == game.id }

        if (friendIndex != -1) {

            currentFavorites.removeAt(friendIndex)
            firebaseStore.collection("favoriteGames")
                .document(game.id.toString())
                .delete()
                .addOnSuccessListener {
                    Log.d("Firestore", "Game removed from favorites: ${game.id}")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error removing game from favorites: ${e.message}")
                }
        } else {

            currentFavorites.add(game)
            firebaseStore.collection("favoriteGames")
                .document(game.id.toString())
                .set(game)
                .addOnSuccessListener {
                    Log.d("Firestore", "Game added to favorites: ${game.id}")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error adding game to favorites: ${e.message}")
                }
        }
        _favoriteGames.value = currentFavorites
    }


    fun isGameFavored(gameId: Int): Boolean {
        return _favoriteGames.value?.any { it.id == gameId } == true
    }


    fun loadFavoriteGames() {
        firebaseStore.collection("favoriteGames")
            .get()
            .addOnSuccessListener { documents ->
                val favorites = documents.map { it.toObject(Game::class.java) }
                _favoriteGames.value = favorites
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error loading favorite books: ${e.message}")
            }
    }
















}





/*private val _videoDetails = MutableLiveData<List<VideoDetail>>()
val videoDetails: LiveData<List<VideoDetail>>
    get() = _videoDetails*/



/* fun loadVideos() {
        viewModelScope.launch {
            _currentResult.value?.guid?.let { guid ->
                val videoDetailsList = repository.loadVideosForGame(guid)
                _videoDetails.value = videoDetailsList
            }
        }
    }*/





/*val dataListVideos = repository.videoResult

*//*fun loadVideos() {

        viewModelScope.launch {

            _currentResult.value?.guid?.let { repository.loadVideosForGame(it) }


        }


    }*/






/*val dataListGenre = repository.genreResult

fun loadGenres() {

    viewModelScope.launch {

        _currentResult.value?.guid?.let { repository.loadGenreForGame(it) }

    }

}*/







/*fun loadVideosForSelectedGame(game: Game) {
    viewModelScope.launch {
        try {
            val gameWithVideos = repository.loadVideosForGame(game)
            _currentResult.value = gameWithVideos
        } catch (e: Exception) {

            Log.e(TAG, "Error loading videos for selected game: ${e.message}", e)
        }
    }
}
*/







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




//GENRES version 2

/*val dataListGenre = repository.genreResult



fun loadGenres() {

    viewModelScope.launch {

        _currentResult.value?.guid?.let { repository.getGenres(it) }

    }

}*/
