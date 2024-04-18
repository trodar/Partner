pluginManagement {
    repositories {
        google()
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
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Partner"
include(":app")
include(":navigation")
include(":core:theme")
include(":features:preach")
include(":core:common")
include(":core:common-impl")
include(":core:utils")
include(":core:room")
include(":features:report")
include(":features:domain")
include(":features:test")
include(":features:notepad")
