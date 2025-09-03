package com.rezazavareh7.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class NetworkConventionPlugin: Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("android-library-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("hilt-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("okhttp-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("retrofit-convention").get().get().pluginId)
        plugins.apply(libs.findPlugin("moshi-convention").get().get().pluginId)
    }
}