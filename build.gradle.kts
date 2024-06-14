import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.SPRING_BOOT) version Versions.SPRING_BOOT
    id(Plugins.SPRING_DEPENDENCY_MANAGEMENT) version Versions.SPRING_DEPENDENCY_MANAGEMENT
    id(Plugins.APPLICATION)
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"

    kotlin(Plugins.JVM) version Versions.KOTLIN
    kotlin(Plugins.SPRING) version Versions.KOTLIN
    kotlin(Plugins.JPA) version Versions.JPA_PLUGIN
    kotlin(Plugins.KAPT) version Versions.KAPT_PLUGIN
}

allprojects {
    group = "com.piikii"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "kotlin")
    apply(plugin = "java-library")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "application")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    dependencies {
        // for kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        // for spring bean configuration
        implementation("org.springframework.boot:spring-boot-autoconfigure")

        // for test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.named("build") {
    dependsOn("clean", "ktlintFormat")
}

tasks.named("bootJar") {
    dependsOn("clean", "ktlintFormat")
}

configure(allprojects.filter { it.name != "piikii-common" }) {
    dependencies {
        implementation(project(":piikii-common"))
    }
}

application {
    mainClass.set("com.piikii.bootstrap.BootstrapApplicationKt")
}

java.sourceCompatibility = JavaVersion.VERSION_21
