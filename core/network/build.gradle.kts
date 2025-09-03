plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.hilt.convention)
    alias(libs.plugins.moshi.convention)
    alias(libs.plugins.retrofit.convention)
    alias(libs.plugins.okhttp.convention)
}

dependencies {

    // Modules
    implementation(projects.core.common)
    implementation(projects.core.database)
}
