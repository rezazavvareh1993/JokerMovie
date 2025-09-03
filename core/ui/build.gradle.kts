plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.kotlin.compose.convention)
    alias(libs.plugins.hilt.convention)
}

dependencies {
    // Compose
    api(libs.androidx.navigation.compose)
    api(libs.androidx.compose.ui)

    // Glide
    implementation(libs.glide.compose)
    ksp(libs.glide.ksp)

    // Coil
//    implementation("io.coil-kt.coil3:coil-compose:3.3.0")
//    implementation("io.coil-kt.coil3:coil-network-okhttp:3.3.0")

    // telephoto
    api(libs.zoomable)

    // Material
    api(libs.androidx.compose.material3)

    // Hilt Navigation
    api(libs.hilt.navigation.compose)

    // Modules
    implementation(projects.core.common)
    api(projects.core.designsystem)
}
