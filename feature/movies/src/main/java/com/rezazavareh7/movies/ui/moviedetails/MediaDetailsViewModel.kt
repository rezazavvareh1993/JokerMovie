package com.rezazavareh7.movies.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.usecase.GetMediaDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailsViewModel
    @Inject
    constructor(
        private val getMediaDetailsUseCase: GetMediaDetailsUseCase,
    ) : ViewModel() {
        private var mMovieDetailsState = MutableStateFlow(MovieDetailsUiState(isLoading = true))
        val mediaDetailsState = mMovieDetailsState.asStateFlow()

        fun onEvent(event: MovieDetailsUiEvent) {
            when (event) {
                is MovieDetailsUiEvent.OnGetMovieDetailsCalled -> getMovieDetails(event.movieId)
                is MovieDetailsUiEvent.OnToastMessageShown ->
                    mMovieDetailsState.update { it.copy(errorMessage = "") }
            }
        }

        private fun getMovieDetails(movieId: Long) {
            viewModelScope.launch {
                val result = getMediaDetailsUseCase(movieId)
                when (result.hasError) {
                    false -> {
                        mMovieDetailsState.update {
                            it.copy(
                                isLoading = false,
                                movieDetailsData = result.movieDetailsData,
                            )
                        }
                    }

                    true -> {
                        mMovieDetailsState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage,
                            )
                        }
                    }
                }
            }
        }
    }
