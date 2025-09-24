package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.SeriesCreditsResponse
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.Role
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import javax.inject.Inject

class SeriesCreditsMapper
    @Inject
    constructor() {
        operator fun invoke(result: Result<SeriesCreditsResponse>): BasicNetworkState<List<Credit>> =
            result.fold(
                onSuccess =
                    { onSuccess(response = it) },
                onFailure =
                    { onFailure(it) },
            )

        private fun onFailure(throwable: Throwable): BasicNetworkState<Nothing> =
            BasicNetworkState.Error(throwable = throwable, message = throwable.message.toString())

        private fun onSuccess(response: SeriesCreditsResponse): BasicNetworkState<List<Credit>> {
            val credits = mutableListOf<Credit>()
            val crews =
                response.crew.filter { it.known_for_department == "Directing" }.map { crew ->
                    with(crew) {
                        credits.add(
                            Credit(
                                id = id,
                                name = name,
                                pathUrl = profile_path ?: "",
                                characterName = "",
                                role = Role.DIRECTOR,
                            ),
                        )
                    }
                }
            val casts =
                response.cast.map { cast ->
                    with(cast) {
                        credits.add(
                            Credit(
                                id = id,
                                name = name,
                                characterName = character,
                                pathUrl = profile_path ?: "",
                                role = Role.ACTOR,
                            ),
                        )
                    }
                }
            return BasicNetworkState.Success(data = credits)
        }
    }
