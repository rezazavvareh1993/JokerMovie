package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.SearchMovieResponse
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.domain.networkstate.GetMoviesNetworkState
import javax.inject.Inject

class SearchMovieMapper
    @Inject
    constructor() {
        operator fun invoke(result: Result<SearchMovieResponse>): GetMoviesNetworkState =
            result.fold(
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
            )

        private fun onFailure(error: Throwable): GetMoviesNetworkState =
            GetMoviesNetworkState.Error(
                message = error.message.toString(),
            )

        private fun onSuccess(data: SearchMovieResponse): GetMoviesNetworkState =
            GetMoviesNetworkState.Success(
                data =
                    data.results.map { movie ->
                        MovieData(
                            name = movie.title,
                            id = movie.id.toLong(),
                            banner = movie.poster_path ?: "",
                            releaseDate = movie.release_date,
                            rate = movie.vote_average.toFloat(),
                        )
                    },
            )
    }
