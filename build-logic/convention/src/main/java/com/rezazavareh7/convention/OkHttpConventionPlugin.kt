package com.rezazavareh7.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class OkHttpConventionPlugin: Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()

        dependencies{
            add("implementation", libs.findLibrary("okhttp3-logging").get())
            add("api", libs.findLibrary("okhttp3").get())
        }
    }
}