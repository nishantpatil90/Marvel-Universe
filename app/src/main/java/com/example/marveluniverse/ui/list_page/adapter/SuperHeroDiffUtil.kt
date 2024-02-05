package com.example.marveluniverse.ui.list_page.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.marveluniverse.domain.model.SuperHero

class SuperHeroDiffUtil : DiffUtil.ItemCallback<SuperHero>() {

    override fun areItemsTheSame(oldItem: SuperHero, newItem: SuperHero): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SuperHero, newItem: SuperHero): Boolean {
        return oldItem == newItem
    }
}
