plugins {
    alias(libs.plugins.android.application.convention)
}

dependencies {

    // Compose & UI
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    // Modules
    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(projects.core.database)
    implementation(projects.core.network)
    implementation(projects.feature.movies)
}
