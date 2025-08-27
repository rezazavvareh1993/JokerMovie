package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.MovieDetailsResponse
import com.rezazavareh7.movies.domain.model.MediaDetailData
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import javax.inject.Inject

class MovieDetailsMapper
    @Inject
    constructor() {
        operator fun invoke(result: Result<MovieDetailsResponse>): BasicNetworkState<MediaDetailData> =
            result.fold(
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
            )

        private fun onFailure(error: Throwable): BasicNetworkState<Nothing> =
            BasicNetworkState.Error(
                message = error.message.toString(),
            )

        private fun onSuccess(data: MovieDetailsResponse): BasicNetworkState<MediaDetailData> =
            BasicNetworkState.Success(
                data =
                    MediaDetailData(
                        name = data.title,
                        id = data.id.toLong(),
                        backdrop = data.backdrop_path ?: "",
                        poster = data.poster_path ?: "",
                        overview = data.overview,
                        genres = data.genres.map { it.name },
                        rate = data.vote_average.toFloat(),
                        voteCount = data.vote_count.toLong(),
                        releaseDate = data.release_date,
                    ),
            )
    }
