package com.example.marveluniverse.ui.list_page

import androidx.recyclerview.widget.RecyclerView
import com.example.marveluniverse.databinding.SuperheroListItemLayoutBinding
import com.example.marveluniverse.domain.model.SuperHero
import com.example.marveluniverse.ui.list_page.ui_action.SuperHeroListUiAction

class SuperHeroListViewHolder(
    private val binding: SuperheroListItemLayoutBinding,
    private val onClick: (SuperHeroListUiAction, String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(superHero: SuperHero) {
        binding.model = superHero
        binding.bookmarkIcon.setOnClickListener {
            onClick(
                SuperHeroListUiAction.FavouriteClick,
                superHero.id
            )
        }
        binding.superHeroItemContainer.setOnClickListener {
            onClick(
                SuperHeroListUiAction.SuperHeroClick,
                superHero.id
            )
        }
        binding.executePendingBindings()
    }
}
