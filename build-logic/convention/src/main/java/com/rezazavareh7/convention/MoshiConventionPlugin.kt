package com.rezazavareh7.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MoshiConventionPlugin: Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("ksp").get().get().pluginId)

        dependencies {
            add("implementation", libs.findLibrary("moshi-converter").get())
            add("implementation", libs.findLibrary("moshi").get())
            add("ksp", libs.findLibrary("moshi-codegen").get())
        }
    }
}
