package com.rezazavareh7.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("android-library-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("kotlin-compose-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("hilt-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("jetbrains-kotlin-serialization").get().get().pluginId)
        plugins.apply(libs.findPlugin("moshi-convention").get().get().pluginId)

        dependencies {
            "implementation"(project(":core:ui"))
            "implementation"(project(":core:common"))
            "implementation"(project(":core:network"))
        }
    }
}
