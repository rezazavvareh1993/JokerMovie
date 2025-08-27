package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveFavoriteItemUseCase
    @Inject
    constructor(
        private val repository: FavoriteRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(mediaData: MediaData) =
            withContext(dispatcher) {
                repository.insertFavoriteItem(mediaData)
            }
    }
