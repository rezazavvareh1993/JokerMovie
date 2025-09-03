package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.MoviesResponse
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import javax.inject.Inject

class MoviesMapper
    @Inject
    constructor() {
        operator fun invoke(result: Result<MoviesResponse>): BasicNetworkState<List<MediaData>> =
            result.fold(onSuccess = { onSuccess(it) }, onFailure = { onFailure(it) })

        private fun onFailure(throwable: Throwable): BasicNetworkState<Nothing> =
            BasicNetworkState.Error(throwable = throwable, message = throwable.message.toString())

        private fun onSuccess(data: MoviesResponse): BasicNetworkState<List<MediaData>> =
            BasicNetworkState.Success(
                data =
                    data.results.map {
                        with(it) {
                            MediaData(
                                mediaCategory = MediaCategory.MOVIE,
                                title = title,
                                id = id.toLong(),
                                posterPath = poster_path ?: "",
                                releaseDate = release_date,
                                voteAverage = vote_average.toFloat(),
                                overview = overview,
                                voteCount = vote_count.toLong(),
                                genres = emptyList(),
                            )
                        }
                    },
            )
    }
