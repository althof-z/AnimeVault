package com.example.animevault.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animevault.databinding.ItemAnimeHomeBinding
import com.example.animevault.ui.adapter.AnimeAdapterListener
import com.example.domain.model.AnimeHome

class AnimeHomeViewHolder(
    private val itemViewBinding: ItemAnimeHomeBinding, private val animeAdapterListener: AnimeAdapterListener

): RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bindAnime(data: AnimeHome){
        itemViewBinding.tvAnimeTitle.text = data.title

        itemViewBinding.ivAnimeCoverHome.load(data.image)

        itemViewBinding.tvAnimeDesc.text = data.desc

    }


}