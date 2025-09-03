plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.hilt.convention)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {

    // Data Store
    implementation(libs.androidx.dataStore.preferences)

    // Serializable
    implementation(libs.kotlinx.serialization.json)

    // Room
    implementation(libs.room.ktx)
//    api(libs.room.paging)
    ksp(libs.room.compiler)
}
