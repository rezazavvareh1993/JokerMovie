// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
}

subprojects {
    plugins.withId("org.jetbrains.kotlin.android") {
        apply(plugin = "org.jlleitschuh.gradle.ktlint")

        afterEvaluate {
            extensions.findByType(org.jlleitschuh.gradle.ktlint.KtlintExtension::class.java)
                ?.apply {
                    android.set(true) // Keep this as it won't affect non-Android modules
                    outputToConsole.set(true)
                    ignoreFailures.set(true)
                    reporters {
                        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
                        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
                    }
                    filter {
                        exclude("**/generated/**")
                        include("**/*.kt")
                    }
                }

            tasks.matching { it.name == "preBuild" || it.name == "compileKotlin" }.configureEach {
                dependsOn("ktlintFormat")
            }
        }
    }
}
