package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.mapper.extension.mapGenres
import com.rezazavareh7.movies.data.model.MovieDetailsResponse
import com.rezazavareh7.movies.domain.model.MediaCategory
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

        private fun onFailure(throwable: Throwable): BasicNetworkState<Nothing> =
            BasicNetworkState.Error(throwable = throwable, message = throwable.message.toString())

        private fun onSuccess(response: MovieDetailsResponse): BasicNetworkState<MediaDetailData> =
            with(response) {
                BasicNetworkState.Success(
                    data =
                        MediaDetailData(
                            name = title,
                            id = id.toLong(),
                            backdrop = backdrop_path ?: "",
                            poster = poster_path ?: "",
                            overview = overview,
                            genres = genres.mapGenres(),
                            rate = vote_average.toFloat(),
                            voteCount = vote_count.toLong(),
                            releaseDate = release_date,
                            category = MediaCategory.MOVIE,
                        ),
                )
            }
    }
