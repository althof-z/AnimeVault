package com.example.animevault.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animevault.databinding.ItemAnimeNewBinding
import com.example.animevault.ui.adapter.AnimeAdapterListener
import com.example.domain.model.Anime
import com.example.domain.model.AnimeHome

class AnimeViewHolder (
    private val itemViewBinding: ItemAnimeNewBinding,
    private val animeAdapterListener: AnimeAdapterListener,
    private val fragmentType: FragmentType

): RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bindAnime(data: Anime){
        itemViewBinding.tvAnimeTitle.text = data.title

        itemViewBinding.ivAnimeCover.load(data.image)

        itemViewBinding.tvAnimeDesc.text = data.synopsis

        itemViewBinding.tvTagYear.text = data.year.toString()

        itemViewBinding.tvTagEpisode.text = data.episode

        itemViewBinding.tvRate.text = data.rate.toString()

        itemViewBinding.btnFavAnime.text = when (fragmentType) {
            FragmentType.FAVORITE -> "Remove From Favorite" // Text for FavoriteFragment
            FragmentType.ANIME -> "Add to Favorite"    // Text for AnimeFragment
        }

        itemViewBinding.btnFavAnime.setOnClickListener{
            animeAdapterListener.onClickFavButton(data)
        }
    }


}

enum class FragmentType {
    FAVORITE,
    ANIME
}