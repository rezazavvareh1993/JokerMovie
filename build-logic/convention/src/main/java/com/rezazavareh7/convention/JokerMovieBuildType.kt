package com.rezazavareh7.convention
enum class JokerMovieBuildType(val applicationIdSuffix: String? = null, val versionNameSuffix: String? = null) {
    DEBUG(".debug", "-debug"),
    RELEASE,
}