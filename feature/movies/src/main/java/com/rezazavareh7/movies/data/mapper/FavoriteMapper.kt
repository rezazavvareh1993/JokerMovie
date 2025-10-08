package com.rezazavareh7.movies.data.mapper

import com.rezazavareh.database.FavoriteEntity
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import javax.inject.Inject

class FavoritesMapper
    @Inject
    constructor() {
        fun mapToData(vararg entity: FavoriteEntity): List<MediaData> {
            val entityList = entity.toList()
            return entityList.map {
                with(it) {
                    MediaData(
                        title = title,
                        id = id,
                        posterPath = posterPath,
                        releaseDate = releaseDate,
                        voteAverage = voteAverage,
                        overview = overview,
                        voteCount = voteCount,
                        genres = emptyList(),
                        mediaCategory = MediaCategory.valueOf(category),
                    )
                }
            }
        }

        fun mapToEntity(vararg data: MediaData): List<FavoriteEntity> {
            val dataList = data.toList()
            return dataList.map {
                with(it) {
                    FavoriteEntity(
                        category = mediaCategory.name,
                        title = title,
                        id = id,
                        posterPath = posterPath,
                        releaseDate = releaseDate,
                        voteAverage = voteAverage,
                        overview = overview,
                        voteCount = voteCount,
                        genres = "",
                    )
                }
            }
        }
    }
