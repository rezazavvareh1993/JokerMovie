package com.rezazavareh7.convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()

        plugins.apply(libs.findPlugin("android-application").get().get().pluginId)
        plugins.apply(libs.findPlugin("kotlin-android-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("kotlin-compose-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("hilt-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("jetbrains-kotlin-serialization").get().get().pluginId)
        plugins.apply(libs.findPlugin("ktlint").get().get().pluginId)

        extensions.getByType(ApplicationExtension::class.java).apply {
            namespace = "com.rezazavareh7.jokermovie"
            compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
            defaultConfig {
                multiDexEnabled = true
                minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
                targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()
                applicationId = "com.rezazavareh7.jokermovie"
                versionCode = libs.findVersion("appVersion").get().requiredVersion.toInt()
                versionName = libs.findVersion("appVersion").get().requiredVersion.toCharArray()
                    .joinToString(".")
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            buildFeatures {
                buildConfig = true
                compose = true
            }

            packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

            buildTypes {
                debug {
                    isMinifyEnabled = false
                    isDebuggable = true
                    versionNameSuffix = JokerMovieBuildType.DEBUG.versionNameSuffix
                }

                release {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro",
                    )
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
                add("implementation", libs.findLibrary("androidx-core-splashscreen").get())
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
                add("debugImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
            }
        }
    }
}
