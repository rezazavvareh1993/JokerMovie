pluginManagement {
    includeBuild("build-logic")
    repositories {
//        google {
//            content {
//                includeGroupByRegex("com\\.android.*")
//                includeGroupByRegex("com\\.google.*")
//                includeGroupByRegex("androidx.*")
//            }
//        }
//        mavenCentral()
//        gradlePluginPortal()
        maven {
            url = uri("http://htscrepo.agri-bank.com:8000/repository/htsc-maven-group/")
            isAllowInsecureProtocol = true
        }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        google()
//        mavenCentral()
        maven {
            url = uri("http://htscrepo.agri-bank.com:8000/repository/htsc-maven-group/")
            isAllowInsecureProtocol = true
        }

    }
}

rootProject.name = "JokerMovie"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":core:common")
include(":core:designsystem")
include(":core:network")
include(":core:ui")
include(":feature:movies")
include(":core:database")
