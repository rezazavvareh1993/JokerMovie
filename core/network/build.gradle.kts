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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    flavorDimensions.add("paliz")

    productFlavors {
        create("dev") {
            dimension = "paliz"
            buildConfigField("String", "AUTH_BASE_URL", "\"https://auth-dev.example.com/PalizAuthDev\"")
            buildConfigField("String", "SETTING_BASE_URL", "\"https://setting-dev.example.com/PalizSettingDev\"")
        }
        create("stage") {
            dimension = "paliz"
            buildConfigField("String", "AUTH_BASE_URL", "\"https://auth-stage.example.com/PalizAuthStage\"")
            buildConfigField("String", "SETTING_BASE_URL", "\"https://setting-stage.example.com/PalizSettingStage\"")
        }
        create("prod") {
            dimension = "paliz"
            buildConfigField("String", "AUTH_BASE_URL", "\"https://auth.example.com/PalizAuthProd\"")
            buildConfigField("String", "SETTING_BASE_URL", "\"https://setting.example.com/PalizSettingProd\"")
        }
    }

    buildTypes {
        getByName("debug")
        getByName("release")
    }

    buildFeatures {
        buildConfig = true
    }
    
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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