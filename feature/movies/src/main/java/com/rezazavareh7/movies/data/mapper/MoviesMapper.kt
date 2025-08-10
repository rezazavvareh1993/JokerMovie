package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.MoviesResponse
import com.rezazavareh7.movies.data.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.model.MovieData
import javax.inject.Inject

class MoviesMapper
    @Inject
    constructor() {
        fun mapToData(result: Result<MoviesResponse>): BasicNetworkState<List<MovieData>> =
            result.fold(
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
            )

        private fun onFailure(throwable: Throwable): BasicNetworkState<List<MovieData>> =
            BasicNetworkState.Error(throwable = throwable, message = throwable.message.toString())

        private fun onSuccess(data: MoviesResponse): BasicNetworkState<List<MovieData>> =
            BasicNetworkState.Success(
                data =
                    data.results.map {
                        with(it) {
                            MovieData(
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
