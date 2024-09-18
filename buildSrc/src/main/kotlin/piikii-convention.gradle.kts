import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val libs = the<LibrariesForLibs>()

plugins {
    `java-library`
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.jpa")
    id("org.jlleitschuh.gradle.ktlint")
}

repositories {
    mavenCentral()
}

group = "com.piikii"
version = "0.0.1-SNAPSHOT"

dependencies {
    annotationProcessor(libs.spring.boot.annotation.processor)
    annotationProcessor(libs.bundles.spring.common)
    annotationProcessor(libs.bundles.kotlin.spring)
    testImplementation(libs.bundles.test.implementation)
    testRuntimeOnly(libs.bundles.test.runtime)
}

// piikii common 의존성 전체 추가
configure(allprojects.filter { it.name != "piikii-common" }) {
    dependencies {
        implementation(project(":piikii-common"))
    }
}

java.toolchain.languageVersion = JavaLanguageVersion.of(libs.versions.javaVersion.get())

tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

configurations.all {
    resolutionStrategy {
        cacheDynamicVersionsFor(10 * 60, TimeUnit.SECONDS)
        cacheChangingModulesFor(4, TimeUnit.HOURS)
    }
}
