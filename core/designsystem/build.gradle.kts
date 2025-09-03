plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.kotlin.compose.convention)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.navigation.compose)

    // Kotlin
    api(libs.kotlinx.serialization.json)

    // Material 3
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.navigationSuite)

    // Modules
    implementation(projects.core.common)
}
