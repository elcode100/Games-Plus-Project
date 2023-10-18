package com.example.games_plus.data

//good good

/*val witcher3 = 41484
val massEffectLegendary = 81128
val gta5 = 36765
val bioShockInfinite = 32317
val frostpunk = 55760
val cyberpunk = 38456
val destiny2 = 52647
val detroitBecomeHuman = 51351
val anno1800 = 61028
val atomicHeart = 68449
val mafiaDefinitiveEdition = 78695
val residentEvilVillage = 78967
val hogwartsLegacy = 80641


private val favGames = "id:$frostpunk|$witcher3|$massEffectLegendary|$gta5|$detroitBecomeHuman|$cyberpunk|$mafiaDefinitiveEdition|$destiny2|$residentEvilVillage|$bioShockInfinite|$hogwartsLegacy|$atomicHeart|$anno1800"*/

//okay okay (...read more - description)



/*lifecycleScope.launch(Dispatchers.IO) {
    val document = Jsoup.parse(it.description?.trim() ?: "")

    document.select("a").forEach { aTag ->
        val href = aTag.attr("href")
        if (href.startsWith("/")) {
            aTag.attr("href", "https://www.giantbomb.com$href")
        }
        if (aTag.text().trim().isEmpty()) {
            aTag.remove()
        }
    }

    val cleanedHtml = document.html()
    val fullSpanned = Html.fromHtml(cleanedHtml, Html.FROM_HTML_MODE_LEGACY)

    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            // Ihr Code zum Erweitern des Textes
            binding.tvDescription.text = fullSpanned
            binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }

    withContext(Dispatchers.Main) {
        val maxLength = 200
        if (fullSpanned.length > maxLength) {
            val shortenedSpanned = SpannableStringBuilder(fullSpanned, 0, maxLength)
            val moreText = "...more"
            val spannableMore = SpannableString(moreText)
            spannableMore.setSpan(ForegroundColorSpan(Color.GRAY), 0, moreText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableMore.setSpan(RelativeSizeSpan(0.8f), 0, moreText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableMore.setSpan(clickableSpan, 0, moreText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            shortenedSpanned.append(spannableMore)
            binding.tvDescription.text = shortenedSpanned
            binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
        } else {
            binding.tvDescription.text = fullSpanned
        }
    }
}*/














//okay okay (...read more - description) Version 2



/*lifecycleScope.launch(Dispatchers.IO) {
    val document = Jsoup.parse(it.description?.trim() ?: "")

    document.select("a").forEach { aTag ->
        val href = aTag.attr("href")
        if (href.startsWith("/")) {
            aTag.attr("href", "https://www.giantbomb.com$href")
        }
        if (aTag.text().trim().isEmpty()) {
            aTag.remove()
        }
    }

    val cleanedHtml = document.html()
    val fullSpanned = Html.fromHtml(cleanedHtml, Html.FROM_HTML_MODE_LEGACY)

    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {

            binding.tvDescription.text = fullSpanned
            binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }

    withContext(Dispatchers.Main) {
        val maxLength = 150
        if (fullSpanned.length > maxLength) {
            val shortenedSpanned = SpannableStringBuilder(fullSpanned, 0, maxLength)
            val moreText = "...more"
            val spannableMore = SpannableString(moreText)
            spannableMore.setSpan(ForegroundColorSpan(Color.GRAY), 0, moreText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableMore.setSpan(RelativeSizeSpan(0.8f), 0, moreText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableMore.setSpan(clickableSpan, 0, moreText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            shortenedSpanned.append(spannableMore)
            binding.tvDescription.text = shortenedSpanned
            binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
        } else {
            binding.tvDescription.text = fullSpanned
        }
    }
}*/









/*
val db = FirebaseFirestore.getInstance()

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
}
*/







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












/*<!--Test 2-->
<!--<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:orientation="vertical">

<androidx.constraintlayout.widget.ConstraintLayout
android:layout_width="match_parent"
android:layout_height="match_parent">

<androidx.constraintlayout.widget.ConstraintLayout
android:id="@+id/inner_title_constrain"
android:visibility="visible"
android:layout_width="match_parent"
android:layout_height="42dp"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent">


<TextView
android:id="@+id/tv_game_title"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:gravity="center"
android:singleLine="false"
android:text="Mafia 2"
android:textColor="@color/dark_grey"
android:textSize="11sp"
android:textStyle="bold"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintWidth_max="130dp" />


</androidx.constraintlayout.widget.ConstraintLayout>


<com.jackandphantom.carouselrecyclerview.view.ReflectionViewContainer
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginTop="5dp"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toBottomOf="@id/inner_title_constrain"
app:reflect_gap="0dp"
app:reflect_relativeDepth="0.5">

<ImageView
android:id="@+id/iv_game_title_image"
android:layout_width="130dp"
android:layout_height="170dp"
android:scaleType="fitXY"
android:src="@drawable/atomic_heart_cover_placeholder" />
</com.jackandphantom.carouselrecyclerview.view.ReflectionViewContainer>



</androidx.constraintlayout.widget.ConstraintLayout>
</LinearLayout>-->



<!--<com.jackandphantom.carouselrecyclerview.view.ReflectionViewContainer


android:layout_width="wrap_content"
android:layout_height="wrap_content"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:reflect_gap="0dp"
app:reflect_relativeDepth="0.5">

<ImageView
android:id="@+id/iv_game_title_image"
android:layout_width="200dp"
android:layout_height="300dp"
android:scaleType="fitXY"
android:src="@drawable/atomic_heart_cover_placeholder" />
</com.jackandphantom.carouselrecyclerview.view.ReflectionViewContainer>-->*/





/*
<!--TEST-->
<!--<androidx.constraintlayout.widget.ConstraintLayout

xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="wrap_content"
android:layout_height="wrap_content">

<androidx.constraintlayout.widget.ConstraintLayout
android:id="@+id/inner_title_constrain"
android:layout_width="match_parent"
android:layout_height="42dp"
android:visibility="visible"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent">


<TextView
android:id="@+id/tv_game_title"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:gravity="center"
android:singleLine="false"
android:text="Mafia 2"
android:textColor="@color/dark_grey"
android:textSize="11sp"
android:textStyle="bold"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintHorizontal_bias="0.0"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintWidth_max="130dp" />


</androidx.constraintlayout.widget.ConstraintLayout>

<com.google.android.material.card.MaterialCardView
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginTop="5dp"
app:cardCornerRadius="1dp"
app:cardElevation="0dp"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toBottomOf="@id/inner_title_constrain">

<androidx.constraintlayout.widget.ConstraintLayout
android:layout_width="match_parent"
android:layout_height="wrap_content">


<com.jackandphantom.carouselrecyclerview.view.ReflectionImageView
android:id="@+id/iv_game_title_image"
android:layout_width="140dp"
android:layout_height="280dp"
android:scaleType="fitXY"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:srcCompat="@drawable/atomic_heart_cover_placeholder" />





</androidx.constraintlayout.widget.ConstraintLayout>


</com.google.android.material.card.MaterialCardView>

</androidx.constraintlayout.widget.ConstraintLayout>-->*/










/*
<!--Etalon-->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:orientation="vertical">

<androidx.constraintlayout.widget.ConstraintLayout
android:layout_width="match_parent"
android:layout_height="match_parent">

<androidx.constraintlayout.widget.ConstraintLayout
android:id="@+id/inner_title_constrain"
android:visibility="visible"
android:layout_width="match_parent"
android:layout_height="42dp"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent">


<TextView
android:id="@+id/tv_game_title"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginStart="8dp"
android:gravity="center"
android:singleLine="false"
android:text="Mafia 2"
android:textColor="@color/dark_grey"
android:textSize="11sp"
android:textStyle="bold"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintWidth_max="130dp" />


</androidx.constraintlayout.widget.ConstraintLayout>

<com.jackandphantom.carouselrecyclerview.view.ReflectionImageView
android:id="@+id/iv_game_title_image"
android:layout_width="140dp"
android:layout_height="280dp"
android:layout_marginTop="5dp"
android:scaleType="fitXY"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toBottomOf="@id/inner_title_constrain"
app:srcCompat="@drawable/atomic_heart_cover_placeholder" />

</androidx.constraintlayout.widget.ConstraintLayout>
</LinearLayout>*/














/*
<LinearLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:orientation="vertical">

<androidx.constraintlayout.widget.ConstraintLayout
android:layout_width="match_parent"
android:layout_height="match_parent">

<TextView
android:id="@+id/tv_game_title"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginTop="16dp"
android:layout_marginBottom="5dp"
android:gravity="center"
android:singleLine="false"
android:text="Mafia 2"
android:textSize="12sp"
android:textStyle="bold"
app:layout_constraintWidth_default="wrap"
app:layout_constraintWidth_max="150dp"
app:layout_constraintBottom_toTopOf="@+id/iv_game_title_image"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent" />

<com.jackandphantom.carouselrecyclerview.view.ReflectionImageView
android:id="@+id/iv_game_title_image"
android:layout_width="150dp"
android:layout_height="300dp"
android:scaleType="fitXY"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toBottomOf="@id/tv_game_title"
app:srcCompat="@drawable/atomic_heart_cover_placeholder"/>

</androidx.constraintlayout.widget.ConstraintLayout>
</LinearLayout>*/




/*
<LinearLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:orientation="vertical">


<androidx.constraintlayout.widget.ConstraintLayout
android:layout_width="match_parent"
android:layout_height="match_parent">


<TextView
android:id="@+id/tv_game_title"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_gravity="center"
android:layout_marginTop="16dp"
android:layout_marginBottom="5dp"
android:singleLine="false"
android:text="The Witcher 3: Wild Hunt"
android:textSize="13sp"
android:textStyle="bold"
app:layout_constraintBottom_toTopOf="@+id/iv_game_title_image"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent" />


<com.jackandphantom.carouselrecyclerview.view.ReflectionImageView
android:id="@+id/iv_game_title_image"
android:layout_width="150dp"
android:layout_height="300dp"
android:scaleType="fitXY"

app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toBottomOf="@id/tv_game_title"
app:srcCompat="@drawable/atomic_heart_cover_placeholder"/>


</androidx.constraintlayout.widget.ConstraintLayout>













</LinearLayout>*/
