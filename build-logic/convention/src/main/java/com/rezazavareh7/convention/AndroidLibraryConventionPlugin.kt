package com.rezazavareh7.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("android-library").get().get().pluginId)
        plugins.apply(libs.findPlugin("kotlin-android-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("ktlint").get().get().pluginId)

        extensions.getByType(LibraryExtension::class.java).apply {
            namespace = "com.rezazavareh7.${project.name}"
            compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()

            defaultConfig {
                minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            buildFeatures {
                buildConfig = true
            }

            packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        dependencies {
            add("implementation", libs.findLibrary("androidx-core-ktx").get())
            add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())

            add("testImplementation", libs.findLibrary("junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
            add("androidTestImplementation", libs.findLibrary("androidx-ui-test-junit4").get())
            add("debugImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
        }
    }
}
