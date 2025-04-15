plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ir.bki.network"
    compileSdk = libs.versions.android.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // OkHttp
    api(libs.okhttp3)
    implementation(libs.okhttp3.logging)

    // Retrofit
    api(libs.retrofit.core)
    api(libs.retrofit.result.adapter)

    // Moshi
    implementation(libs.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Modules
    implementation(projects.core.common)

    testImplementation (libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}