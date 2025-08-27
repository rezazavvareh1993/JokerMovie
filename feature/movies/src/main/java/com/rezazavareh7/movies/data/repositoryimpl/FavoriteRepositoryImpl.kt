package com.rezazavareh7.movies.data.repositoryimpl

import com.rezazavareh.database.FavoriteDao
import com.rezazavareh7.movies.data.mapper.FavoritesMapper
import com.rezazavareh7.movies.domain.model.FavoriteData
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepositoryImpl
    @Inject
    constructor(
        private val favoriteDao: FavoriteDao,
        private val favoritesMapper: FavoritesMapper,
    ) : FavoriteRepository {
        override fun fetchFavorites(category: String): Flow<List<FavoriteData>> =
            flow {
                val entities =
                    favoriteDao.getFavorites(category).collect {
                        if (it.isNotEmpty()) {
                            val dataList = favoritesMapper.mapToData(*it.toTypedArray())
                            emit(dataList)
                        }
                    }
            }

        override suspend fun insertFavoriteItem(mediaData: MediaData) {
            favoriteDao.saveFavoriteItem(favoritesMapper.mapToEntity(mediaData).first())
        }

        override suspend fun deleteFavoriteItemById(id: Long) = favoriteDao.deleteFavoriteItemById(id)

        override suspend fun findItemById(
            mediaCategory: MediaCategory,
            id: Long,
        ): FavoriteData? {
            val foundEntity = favoriteDao.findItemById(category = mediaCategory.toString(), id = id)
            return foundEntity?.let {
                favoritesMapper.mapToData(foundEntity).first()
            }
        }
    }
