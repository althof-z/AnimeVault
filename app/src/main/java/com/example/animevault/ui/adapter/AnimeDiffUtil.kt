package com.example.animevault.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.AnimeHome

class AnimeDiffUtil : DiffUtil.ItemCallback<AnimeHome>() {

    override fun areItemsTheSame(oldItem: AnimeHome, newItem: AnimeHome): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: AnimeHome, newItem: AnimeHome): Boolean {
        return oldItem.title == newItem.title
    }
}