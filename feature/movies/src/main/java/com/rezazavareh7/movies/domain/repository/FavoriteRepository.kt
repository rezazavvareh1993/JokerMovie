package com.rezazavareh7.movies.domain.repository

import com.rezazavareh7.movies.domain.model.FavoriteData
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun fetchFavorites(category: String): Flow<List<FavoriteData>>

    suspend fun insertFavoriteItem(mediaData: MediaData)

    suspend fun deleteFavoriteItemById(id: Long)

    suspend fun findItemById(
        mediaCategory: MediaCategory,
        id: Long,
    ): FavoriteData?
}
