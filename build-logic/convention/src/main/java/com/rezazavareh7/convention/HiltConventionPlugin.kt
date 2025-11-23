package com.rezazavareh7.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin: Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("hilt").get().get().pluginId)
        plugins.apply(libs.findPlugin("ksp").get().get().pluginId)

        dependencies {
            add("implementation", libs.findLibrary("hilt-android").get())
            add("ksp", libs.findLibrary("hilt-compiler").get())
        }
    }
}