package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.model.Category
import com.rezazavareh7.movies.domain.model.FavoriteResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavoritesUseCase
    @Inject
    constructor(
        private val repository: MoviesRepository,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        suspend operator fun invoke(category: Category): FavoriteResult =
            withContext(dispatcher) {
                FavoriteResult(favoriteList = repository.fetchFavorites(category))
            }
    }
