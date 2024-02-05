package com.example.marveluniverse.ui.list_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.marveluniverse.data.data_source.local.model.Favourites
import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto
import com.example.marveluniverse.domain.model.SuperHero
import com.example.marveluniverse.domain.use_case.GetFavouritesUseCase
import com.example.marveluniverse.domain.use_case.GetSuperHeroesUseCase
import com.example.marveluniverse.domain.use_case.UpdateFavouriteUseCase
import com.example.marveluniverse.ui.list_page.ui_action.SuperHeroListUiAction
import com.example.marveluniverse.ui.list_page.ui_state.SuperHeroListUiState
import com.example.marveluniverse.ui.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    getSuperHeroesUseCase: GetSuperHeroesUseCase,
    getFavouritesUseCase: GetFavouritesUseCase,
) : ViewModel() {

    val superHeroesList: Flow<PagingData<SuperHero>>
    private val _uiState: MutableStateFlow<Event<SuperHeroListUiState?>> =
        MutableStateFlow(Event(null))
    val uiState = _uiState.asStateFlow()

    init {
        superHeroesList = getSuperHeroesUseCase().cachedIn(viewModelScope)
            .combine(
                flow = getFavouritesUseCase(),
                transform = { pagingData: PagingData<SuperHeroDto>, favourites: List<Favourites> ->
                    pagingData.map { superHeroDto ->
                        SuperHero(
                            id = superHeroDto.id,
                            thumbnail = superHeroDto.thumbnail,
                            name = superHeroDto.name,
                            description = superHeroDto.description,
                            isFavourite = favourites.any { it.id == superHeroDto.id }
                        )
                    }
                })
    }

    fun handleUiEvents(uiAction: SuperHeroListUiAction, superHeroId: String) {
        when (uiAction) {
            SuperHeroListUiAction.FavouriteClick -> toggleFavourite(superHeroId)
            SuperHeroListUiAction.SuperHeroClick -> onDetailClick(superHeroId)
        }
    }

    private fun onDetailClick(superHeroId: String) {
        _uiState.value = Event(SuperHeroListUiState.LaunchDetailScreen(superHeroId))
    }

    private fun toggleFavourite(superHeroId: String) {
        viewModelScope.launch {
            updateFavouriteUseCase(superHeroId)
        }
    }
}
