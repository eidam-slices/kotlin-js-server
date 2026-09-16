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
            api(libs.kotlinx.coroutines.core)
        }
    }
}
