pluginManagement {
    repositories {
//        gradlePluginPortal()
//        google()
        maven {
            url = uri("http://htscrepo.agri-bank.com:8000/repository/htsc-maven-group/")
            isAllowInsecureProtocol = true
        }

    }
}

dependencyResolutionManagement {
    repositories {
//        google {
//            content {
//                includeGroupByRegex("com\\.android.*")
//                includeGroupByRegex("com\\.google.*")
//                includeGroupByRegex("androidx.*")
//            }
//        }
//        mavenCentral()
        maven {
            url = uri("http://htscrepo.agri-bank.com:8000/repository/htsc-maven-group/")
            isAllowInsecureProtocol = true
        }

    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")