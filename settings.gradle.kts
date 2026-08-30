@file:Suppress("UnstableApiUsage")

pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencyResolutionManagement.repositories {
    mavenCentral()
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":core")
include(":node")
include(":sample")
include(":temp")

rootProject.name = "kotlin-js-server"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")