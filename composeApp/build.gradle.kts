import org.jetbrains.compose.reload.gradle.ComposeHotRun
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.compose.hot-reload") version "1.0.0-beta06"
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("portfolio")
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "portfolio.js"
            }
        }
    }

    js {
        outputModuleName.set("portfolio")
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "portfolio.js"
            }
        }
    }

    jvm()

    tasks.withType<ComposeHotRun>().configureEach {
        mainClass.set("fr.outadoc.portfolio.MainKt")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}


