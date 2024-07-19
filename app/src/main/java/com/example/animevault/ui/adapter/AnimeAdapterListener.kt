package com.example.animevault.ui.adapter

import com.example.domain.model.Anime

interface AnimeAdapterListener {
    fun onClickFavButton(data: Anime)
}