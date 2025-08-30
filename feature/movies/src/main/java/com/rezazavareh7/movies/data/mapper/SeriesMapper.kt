package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.SeriesResponse
import com.rezazavareh7.movies.data.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import javax.inject.Inject

class SeriesMapper
    @Inject
    constructor() {
        fun mapToData(result: Result<SeriesResponse>): BasicNetworkState<List<MediaData>> =
            result.fold(
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
            )

        private fun onFailure(throwable: Throwable): BasicNetworkState<Nothing> =
            BasicNetworkState.Error(throwable = throwable, message = throwable.message.toString())

        private fun onSuccess(data: SeriesResponse): BasicNetworkState<List<MediaData>> =
            BasicNetworkState.Success(
                data =
                    data.results.map {
                        with(it) {
                            MediaData(
                                mediaCategory = MediaCategory.SERIES,
                                title = name,
                                id = id.toLong(),
                                posterPath = poster_path ?: "",
                                releaseDate = first_air_date,
                                voteAverage = vote_average.toFloat(),
                                overview = overview,
                                voteCount = vote_count.toLong(),
                                genres = emptyList(),
                            )
                        }
                    },
            )
    }
