package com.example.animevault.ui.adapter

import com.example.domain.model.Anime
import com.example.domain.model.AnimeHome

interface AnimeAdapterListener {
    fun onClickFavButton(data: Anime)
}