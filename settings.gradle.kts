pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CryptoMarket"
include(":app")
include(":core:application")
include(":core:utils")
include(":data:remote:network")
include(":core:configuration")
include(":data:remote")
include(":data:storage")
include(":ui:design")
include(":ui:elements")
include(":ui:scenes")
include(":data:device")
