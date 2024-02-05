package com.example.marveluniverse.ui.list_page.ui_action

sealed interface SuperHeroListUiAction {
    data object FavouriteClick : SuperHeroListUiAction
    data object SuperHeroClick : SuperHeroListUiAction
}
