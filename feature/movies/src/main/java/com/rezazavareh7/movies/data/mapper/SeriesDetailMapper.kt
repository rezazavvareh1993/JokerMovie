package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.SeriesDetailResponse
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaDetailData
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import javax.inject.Inject

class SeriesDetailMapper
    @Inject
    constructor() {
        operator fun invoke(result: Result<SeriesDetailResponse>): BasicNetworkState<MediaDetailData> =
            result.fold(
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
            )

        private fun onFailure(throwable: Throwable): BasicNetworkState<Nothing> =
            BasicNetworkState.Error(throwable = throwable, message = throwable.message.toString())

        private fun onSuccess(response: SeriesDetailResponse): BasicNetworkState<MediaDetailData> =
            with(response) {
                BasicNetworkState.Success(
                    data =
                        MediaDetailData(
                            name = name,
                            id = id.toLong(),
                            backdrop = backdrop_path ?: "",
                            poster = poster_path ?: "",
                            overview = overview,
                            genres = genres.map { it.name },
                            rate = vote_average.toFloat(),
                            voteCount = vote_count.toLong(),
                            releaseDate = first_air_date ?: "",
                            category = MediaCategory.SERIES,
                        ),
                )
            }
    }
