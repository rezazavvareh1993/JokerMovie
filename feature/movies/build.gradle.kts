plugins {
    alias(libs.plugins.feature.convention)
}

dependencies {

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.room.paging)

    // Modules
    implementation(projects.core.ui)
    implementation(projects.core.network)
    implementation(projects.core.common)
    implementation(projects.core.database)
}
