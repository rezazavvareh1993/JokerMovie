package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.SeriesCreditsResponse
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.Role
import javax.inject.Inject

class SeriesCreditsMapper
    @Inject
    constructor() {
        operator fun invoke(response: SeriesCreditsResponse): List<Credit> {
            val credits = mutableListOf<Credit>()
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
            return credits
        }
    }
