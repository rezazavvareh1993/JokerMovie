plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.hilt.convention)
    alias(libs.plugins.moshi.convention)
}

dependencies {

    // Timber
    api(libs.timber)

    // DataStore
    api(libs.androidx.dataStore.preferences)
}
