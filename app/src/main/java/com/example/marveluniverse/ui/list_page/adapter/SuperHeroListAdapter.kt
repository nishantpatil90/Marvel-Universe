package com.example.marveluniverse.ui.list_page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.marveluniverse.databinding.SuperheroListItemLayoutBinding
import com.example.marveluniverse.domain.model.SuperHero
import com.example.marveluniverse.ui.list_page.viewholder.SuperHeroListViewHolder
import com.example.marveluniverse.ui.list_page.ui_action.SuperHeroListUiAction

class SuperHeroListAdapter(
    private val onClick: (SuperHeroListUiAction, String) -> Unit,
) : PagingDataAdapter<SuperHero, SuperHeroListViewHolder>(SuperHeroDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroListViewHolder {
        val binding = SuperheroListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SuperHeroListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: SuperHeroListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}