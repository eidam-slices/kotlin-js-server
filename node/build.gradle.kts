plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    js {
        nodejs()
        binaries.library()
    }

    sourceSets {
        jsMain.dependencies {
            api(projects.core)
            implementation(libs.kotlin.wrappers.node)
        }
    }
}