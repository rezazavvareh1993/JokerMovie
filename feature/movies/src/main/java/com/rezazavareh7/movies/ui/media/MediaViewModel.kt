package com.rezazavareh7.movies.ui.media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.usecase.RemoveFavoriteItemUseCase
import com.rezazavareh7.movies.domain.usecase.SaveFavoriteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel
    @Inject
    constructor(
        private val saveFavoriteItemUseCase: SaveFavoriteItemUseCase,
        private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase,
    ) : ViewModel() {
        private var mMediaState = MutableStateFlow(MediaUiState())
        val mediaState: StateFlow<MediaUiState> = mMediaState.asStateFlow()

        fun onEvent(event: MediaUiEvent) {
            when (event) {
                is MediaUiEvent.OnCurrentTabRowChanged ->
                    currentTabRowChangeHandler(newTab = event.tabIndex, oldTab = event.oldIndex)

                is MediaUiEvent.OnLikeMovie -> saveFavoriteMovie(event.mediaData)

                is MediaUiEvent.OnDislikeMovie -> removeFavoriteMovie(event.mediaData)
            }
        }

        private fun currentTabRowChangeHandler(
            newTab: Int,
            oldTab: Int,
        ) {
            val currentTabRowType = MediaCategory.entries[newTab]
            mMediaState.update {
                it.copy(
                    currentTabRowIndex = newTab,
                    currentTabRowType = currentTabRowType,
                )
            }
        }

        private fun saveFavoriteMovie(mediaData: MediaData) {
            viewModelScope.launch {
                viewModelScope.launch {
                    saveFavoriteItemUseCase(mediaData)
                }
            }
        }

        private fun removeFavoriteMovie(mediaData: MediaData) {
            viewModelScope.launch {
                viewModelScope.launch {
                    removeFavoriteItemUseCase(mediaData.id)
                }
            }
        }
    }
