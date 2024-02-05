package com.example.marveluniverse.ui.detail_page

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marveluniverse.domain.model.SuperHero
import com.example.marveluniverse.domain.use_case.GetSuperHeroForIdUseCase
import com.example.marveluniverse.domain.use_case.UpdateFavouriteUseCase
import com.example.marveluniverse.ui.detail_page.ui_action.SuperHeroDetailUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperHeroDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSuperHeroForIdUseCase: GetSuperHeroForIdUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
) : ViewModel() {

    val id = savedStateHandle.get<String>(CHARACTER_ID) ?: ""
    val superHero = getSuperHeroForIdUseCase(id)

    fun handleUiAction(uiEvent: SuperHeroDetailUiAction) {
        when (uiEvent) {
            SuperHeroDetailUiAction.FavouriteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewModelScope.launch {
                        updateFavouriteUseCase(id)
                    }
                }
            }
        }
    }

    companion object {
        const val CHARACTER_ID = "character_id"
    }
}
