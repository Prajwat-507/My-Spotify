import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo

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
        maven { url = uri("https://jitpack.io" )}
    }
}


rootProject.name = "MySpotify"
include(":app")
 