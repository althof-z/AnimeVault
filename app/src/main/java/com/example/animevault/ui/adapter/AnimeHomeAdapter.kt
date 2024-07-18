package com.example.animevault.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.animevault.databinding.ItemAnimeHomeBinding
import com.example.animevault.ui.viewholder.AnimeHomeViewHolder
import com.example.domain.model.AnimeHome

class AnimeHomeAdapter(private val animeAdapterListener: AnimeAdapterListener)
    : ListAdapter<AnimeHome, AnimeHomeViewHolder>(AnimeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHomeViewHolder {
        return AnimeHomeViewHolder(
            itemViewBinding = ItemAnimeHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            animeAdapterListener = animeAdapterListener,
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: AnimeHomeViewHolder, position: Int) {
        holder.bindAnime(getItem(position))
    }
}