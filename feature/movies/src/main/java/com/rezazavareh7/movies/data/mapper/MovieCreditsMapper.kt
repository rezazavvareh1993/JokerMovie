package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.MovieCreditsResponse
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.Role
import javax.inject.Inject

class MovieCreditsMapper
    @Inject
    constructor() {
        operator fun invoke(response: MovieCreditsResponse): List<Credit> {
            val credits = mutableListOf<Credit>()
            response.crew.filter { it.job == "Director" }.map { crew ->
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
