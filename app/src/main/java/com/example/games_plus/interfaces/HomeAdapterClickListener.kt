package com.example.games_plus.interfaces

import com.example.games_plus.data.models.games.Game

interface HomeAdapterClickListener {
    fun onGameTitleClick(item: Game)
}

