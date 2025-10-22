import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.compose.compiler)
    alias(libs.plugins.jetbrains.compose.hotReload)
    alias(libs.plugins.jetbrains.kotlinx.serialization)
    alias(libs.plugins.icerock.resources.multiplatform)
    alias(libs.plugins.arturbosch.detekt)
    jacoco
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.uiUtil)
            implementation(compose.material)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.materialIconsExtended)
            implementation(libs.icerock.resources)
            implementation(libs.icerock.resources.compose)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.jetbrains.androidx.navigation.compose)
            implementation(libs.jetbrains.kotlinx.serialization.json)
            implementation(libs.google.libs.phonenumber)
            implementation(libs.touchlab.kermit)
            implementation(libs.gosyer.appdirs)
        }
        commonTest.dependencies {
            implementation(libs.tests.jetbrains.kotlin.test)
            implementation(libs.tests.jetbrains.kotlinx.coroutines)
            implementation(libs.tests.junit.api)
            implementation(libs.tests.junit.params)
            implementation(libs.tests.junit.jupiter)
            implementation(libs.tests.icerock.resources)
            implementation(libs.tests.androidx.navigation)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.jetbrains.exposed.core)
            implementation(libs.jetbrains.exposed.jdbc)
            implementation(libs.jetbrains.exposed.kotlin.datetime)
            implementation(libs.jetbrains.exposed.money)
            implementation(libs.database.h2)
            implementation(libs.java.money)
        }
    }
}

compose {
    desktop {
        application {
            mainClass = "com.isuponev.tutordb.desktop.MainKt"
            nativeDistributions {
                targetFormats(
                    TargetFormat.Exe,
                    TargetFormat.Msi,
                    TargetFormat.Deb,
                    TargetFormat.AppImage,
                    TargetFormat.Dmg,
                )
                packageName = "com.isuponev.tutordb"
                packageVersion = "1.0.0"
                linux {
                    iconFile.set(layout.projectDirectory.file("src/commonMain/composeResources/drawable/logo.jpg"))
                }
            }
        }
    }
}

multiplatformResources {
    resourcesPackage.set("com.isuponev.tutordb.core.resources")
    resourcesClassName.set("SharedResources")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    parallel = true
    disableDefaultRuleSets = false
    ignoreFailures = false
    config.setFrom("${rootProject.rootDir}/config/detekt.yml")
    source.setFrom(
        "src/jvmMain/kotlin",
        "src/commonMain/kotlin"
    )
    baseline = file("$projectDir/config/baseline.xml")
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
        sarif.required.set(false)
        md.required.set(true)
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    finalizedBy(tasks.named("jacocoTestReport"))
    outputs.cacheIf { false }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    group = "verification"
    description = "Generates JaCoCo test coverage report"
    dependsOn(tasks.named("allTests"))

    sourceDirectories.setFrom(files("src/jvmMain/kotlin", "src/commonMain/kotlin"))
    classDirectories.setFrom(files("build/classes/kotlin/jvm/main"))
    executionData.setFrom(files("build/jacoco/jvmTest.exec"))

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)

        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html"))
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/xml/jacocoTestReport.xml"))
    }

    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it).apply {
                exclude(
                    // Generated sources by Compose Resources
                    "**/META-INF/**",
                    "**/composeapp/**",
                    "**/widgets/**",
                    "**/resources/**" // moko-resources generation
                )
            }
        })
    )

    outputs.cacheIf { false }
}
