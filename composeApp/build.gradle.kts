import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.compose.compiler)
    alias(libs.plugins.jetbrains.compose.hotReload)
    alias(libs.plugins.jetbrains.dokka)
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
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.jetbrains.exposed.core)
            implementation(libs.jetbrains.exposed.jdbc)
            implementation(libs.jetbrains.exposed.kotlin.datetime)
            implementation(libs.jetbrains.exposed.money)
            implementation(libs.database.h2)
            implementation(libs.java.money)
            implementation(libs.google.libs.phonenumber)
        }
        commonTest.dependencies {
            implementation(libs.tests.jetbrains.kotlin.test)
            implementation(libs.tests.junit.api)
            implementation(libs.tests.junit.params)
            implementation(libs.tests.junit.jupiter)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

compose {
    resources {
        publicResClass = true
    }
}

compose.desktop {
    application {
        mainClass = "com.isuponev.tutordb.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.isuponev.tutordb"
            packageVersion = "1.0.0"
        }
    }
}

dokka {
    moduleName.set(rootProject.name)
    moduleVersion.set(rootProject.version.toString())
    dokkaPublications.html {
        outputDirectory.set(rootDir.resolve("docs/html"))
    }
    dokkaSourceSets.named("commonMain") {
        displayName.set("${rootProject.name} Core")
        includes.from("src/commonMain/README.md")
    }
    dokkaSourceSets.named("jvmMain") {
        displayName.set("${rootProject.name} Desktop")
        includes.from("src/jvmMain/README.md")
    }
    pluginsConfiguration.html {
        footerMessage.set("(c) Ilya Suponev")
    }
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
                    "**/composeapp/**"
                )
            }
        })
    )
}
