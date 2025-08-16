package com.rezazavareh7.movies.data.mapper

import com.rezazavareh.database.FavoriteEntity
import com.rezazavareh7.movies.domain.model.FavoriteData
import com.rezazavareh7.movies.domain.model.MovieData
import javax.inject.Inject

class FavoritesMapper
    @Inject
    constructor() {
        fun mapToData(vararg entity: FavoriteEntity): List<FavoriteData> {
            val entityList = entity.toList()
            return entityList.map {
                with(it) {
                    FavoriteData(
                        title = title,
                        id = id,
                        posterPath = posterPath,
                        releaseDate = releaseDate,
                        voteAverage = voteAverage,
                        overview = overview,
                        voteCount = voteCount,
                        genres = emptyList(),
                    )
                }
            }
        }

        fun mapToEntity(vararg data: FavoriteData): List<FavoriteEntity> {
            val dataList = data.toList()
            return dataList.map {
                with(it) {
                    FavoriteEntity(
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

        fun mapToEntity(vararg data: MovieData): List<FavoriteEntity> {
            val dataList = data.toList()
            return dataList.map {
                with(it) {
                    FavoriteEntity(
                        category = category.toString(),
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
