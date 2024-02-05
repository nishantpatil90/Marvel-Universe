package com.example.marveluniverse.ui.list_page.ui_state

sealed interface SuperHeroListUiState {

     @JvmInline
     value class LaunchDetailScreen(val id: String): SuperHeroListUiState
}
