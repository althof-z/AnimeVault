package com.example.animevault.ui.adapter

import AnimePlusDiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.animevault.databinding.ItemAnimeNewBinding
import com.example.animevault.ui.viewholder.AnimeViewHolder
import com.example.animevault.ui.viewholder.FragmentType
import com.example.domain.model.Anime

class AnimeAdapter (private val animeAdapterListener: AnimeAdapterListener, private val fragmentType: FragmentType)
    : ListAdapter <Anime, AnimeViewHolder>(AnimePlusDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(
            itemViewBinding = ItemAnimeNewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            animeAdapterListener = animeAdapterListener,
            fragmentType =fragmentType
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bindAnime(getItem(position))
    }
}