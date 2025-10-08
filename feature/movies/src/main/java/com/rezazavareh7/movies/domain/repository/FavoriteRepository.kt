package com.rezazavareh7.movies.domain.repository

import com.rezazavareh7.movies.domain.model.MediaData
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<MediaData>>

    suspend fun insertFavoriteItem(mediaData: MediaData)

    suspend fun deleteFavoriteItemById(id: Long)
}
