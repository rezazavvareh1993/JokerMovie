package com.rezazavareh7.movies.domain.model

data class Credit(
    val id: Long,
    val name: String,
    val pathUrl: String,
    val characterName: String,
    val role: Role,
)

enum class Role {
    ACTING,
    DIRECTING,
}
