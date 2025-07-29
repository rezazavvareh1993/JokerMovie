package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.MovieDetailsResponse
import com.rezazavareh7.movies.domain.model.MovieDetailData
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import javax.inject.Inject

class MovieDetailsMapper
    @Inject
    constructor() {
        operator fun invoke(result: Result<MovieDetailsResponse>): GetMovieDetailNetworkState =
            result.fold(
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
            )

        private fun onFailure(error: Throwable): GetMovieDetailNetworkState =
            GetMovieDetailNetworkState.Error(
                message = error.message.toString(),
            )

        private fun onSuccess(data: MovieDetailsResponse): GetMovieDetailNetworkState =
            GetMovieDetailNetworkState.Success(
                data =
                    MovieDetailData(
                        name = data.title,
                        id = data.id.toLong(),
                        banner = data.poster_path ?: "",
                        overview = data.overview,
                        genres = data.genres.map { it.name },
                        rate = data.vote_average.toFloat(),
                        voteCount = data.vote_count.toLong(),
                        releaseDate = data.release_date,
                    ),
            )
    }
