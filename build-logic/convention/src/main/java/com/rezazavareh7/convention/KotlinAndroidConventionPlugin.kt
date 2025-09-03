package com.rezazavareh7.convention

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension

class KotlinAndroidConventionPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("kotlin-android").get().get().pluginId)
        extensions.configure<KotlinAndroidExtension> {
            jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion.toInt()))
            }
        }
    }
}