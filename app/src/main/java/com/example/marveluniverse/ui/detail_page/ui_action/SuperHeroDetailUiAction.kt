package com.example.marveluniverse.ui.detail_page.ui_action

sealed interface SuperHeroDetailUiAction {
    data object FavouriteClick : SuperHeroDetailUiAction
}
