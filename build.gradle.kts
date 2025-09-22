plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose.multiplatform) apply false
    alias(libs.plugins.jetbrains.kotlin.compose.compiler) apply false
    alias(libs.plugins.jetbrains.compose.hotReload) apply false
    alias(libs.plugins.arturbosch.detekt) apply false
}

rootProject.version = "1.0.0"