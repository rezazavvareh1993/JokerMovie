package com.rezazavareh7.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import kotlin.jvm.java

fun Project.getLibs(): VersionCatalog =
    extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.android(): LibraryExtension =
    extensions.getByType(LibraryExtension::class.java)