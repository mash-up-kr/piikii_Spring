import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinSpring)
    alias(libs.plugins.kotlinJpa)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.ktLint)
    application
}

allprojects {
    group = "com.piikii"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile>().configureEach {
        options.isIncremental = true
        options.isFork = true
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.springframework.boot")
        plugin("kotlin")
        plugin("java-library")
        plugin("kotlin-jpa")
        plugin("io.spring.dependency-management")
        plugin("kotlin-kapt")
        plugin("application")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.boot:spring-boot-autoconfigure")
        implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        mainClass.set("com.piikii.bootstrap.PiikiiBootstrapApplicationKt")
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.named("bootJar") {
        dependsOn("ktlintFormat")
    }
}

configure(allprojects.filter { it.name != "piikii-common" }) {
    dependencies {
        implementation(project(":piikii-common"))
    }
}

java.toolchain.languageVersion = JavaLanguageVersion.of(libs.versions.javaVersion.get())

tasks.bootJar {
    enabled = false
}

configurations.all {
    resolutionStrategy {
        cacheDynamicVersionsFor(10 * 60, TimeUnit.SECONDS)
        cacheChangingModulesFor(4, TimeUnit.HOURS)
    }
}

develocity {
    buildScan {
        projectId.set("piikii-application")

        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")

        link("VCS", "https://github.com/mash-up-kr/piikii_Spring/tree/develop")
        value("hash", System.getenv("CURRENT_GIT_SHA"))
        value("status", System.getenv("STATUS"))

        buildFinished {
            if (this.failures.isNotEmpty()) {
                val failureMessages = this.failures.mapNotNull { it.message }.joinToString(",") { it }
                value("Failed with", failureMessages)
            }
        }
        // default false option: 추후 많은 빌드로 인해 이슈가 생길 때를 대비
        // publishing.onlyIf { false }
    }
}
