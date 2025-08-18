package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveFavoriteItemUseCase
    @Inject
    constructor(
        private val repository: MoviesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(id: Long) =
            withContext(dispatcher) {
                repository.deleteFavoriteItemById(id)
            }
    }
