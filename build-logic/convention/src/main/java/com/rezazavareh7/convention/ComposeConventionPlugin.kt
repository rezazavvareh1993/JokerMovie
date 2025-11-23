package com.rezazavareh7.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("kotlin-compose").get().get().pluginId)
        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", (platform(bom)))
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling").get())
            add("implementation", libs.findLibrary("androidx.compose.ui").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
        }
    }
}
