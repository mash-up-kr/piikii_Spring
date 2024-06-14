import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

java.sourceCompatibility = JavaVersion.VERSION_21

application {
    mainClass.set("com.piikii.bootstrap.BootstrapApplicationKt")
}

plugins {
    id(Plugins.springBoot) version Versions.springBoot
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    id("application")
    kotlin(Plugins.kotlinJvm) version Versions.kotlin
    kotlin(Plugins.kotlinSpring) version Versions.kotlin
    kotlin(Plugins.kotlinJpa) version Versions.kotlinJpa
    kotlin(Plugins.kotlinKapt) version Versions.kotlinKapt
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
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "21"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

configure(allprojects.filter { it.name != "piikii-common" }) {
    dependencies {
        implementation(project(":piikii-common"))
    }
}
