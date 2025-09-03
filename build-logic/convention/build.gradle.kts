plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion.toInt()))
    }
}

gradlePlugin {
    plugins {
        register("AndroidApplicationConventionPlugin") {
            id = libs.plugins.android.application.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.AndroidApplicationConventionPlugin"
        }

        register("AndroidLibraryConventionPlugin") {
            id = libs.plugins.android.library.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.AndroidLibraryConventionPlugin"
        }

        register("KotlinAndroidConventionPlugin") {
            id = libs.plugins.kotlin.android.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.KotlinAndroidConventionPlugin"
        }

        register("ComposeConventionPlugin") {
            id = libs.plugins.kotlin.compose.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.ComposeConventionPlugin"
        }

        register("HiltConventionPlugin") {
            id = libs.plugins.hilt.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.HiltConventionPlugin"
        }

        register("FeatureConventionPlugin") {
            id = libs.plugins.feature.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.FeatureConventionPlugin"
        }

        register("MoshiConventionPlugin") {
            id = libs.plugins.moshi.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.MoshiConventionPlugin"
        }

        register("RetrofitConventionPlugin") {
            id = libs.plugins.retrofit.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.RetrofitConventionPlugin"
        }
        register("OkHttpConventionPlugin") {
            id = libs.plugins.okhttp.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.OkHttpConventionPlugin"
        }

        register("NetworkConventionPlugin") {
            id = libs.plugins.network.convention.get().pluginId
            implementationClass = "com.rezazavareh7.convention.NetworkConventionPlugin"
        }
    }
}