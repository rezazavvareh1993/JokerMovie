import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "ir.bki.paliz"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "ir.bki.paliz"
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()
        versionCode =
            libs.versions.appVersion
                .get()
                .toInt()
        versionName =
            libs.versions.appVersion
                .get()
                .toCharArray()
                .joinToString(".")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add("paliz")

    productFlavors {
        create("dev") {
            dimension = "paliz"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "WALLET_BASE_URL", "\"http://10.0.85.111:1010/\"")
            buildConfigField(
                "String",
                "SETTING_BASE_URL",
                "\"https://setting-dev.example.com/PalizSettingDev\"",
            )
        }
        create("uat") {
            dimension = "paliz"
            applicationIdSuffix = ".uat"
            versionNameSuffix = "-uat"
            buildConfigField("String", "WALLET_BASE_URL", "\"https://wallet-uat.bki.ir/walletbackend/\"")

            buildConfigField(
                "String",
                "SETTING_BASE_URL",
                "\"https://setting-stage.example.com/PalizSettingStage\"",
            )
        }
        create("prod") {
            dimension = "paliz"
            buildConfigField(
                "String",
                "WALLET_BASE_URL",
                "\"https://wallet.bki.ir/walletbackend/\"",
            )

            buildConfigField(
                "String",
                "SETTING_BASE_URL",
                "\"https://setting.example.com/PalizSettingProd\"",
            )
        }
    }

    val keystoreProperties =
        Properties().apply {
            load(File(rootDir, "keystore.properties").inputStream())
        }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties["KEYSTORE_FILE"] as String)
            storePassword = keystoreProperties["KEYSTORE_PASSWORD"] as String
            keyAlias = keystoreProperties["KEY_ALIAS"] as String
            keyPassword = keystoreProperties["KEY_PASSWORD"] as String
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            versionNameSuffix = "-debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Compose & UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Modules
    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(projects.core.network)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
