project(":piikii-input-http") {
    dependencies {
        implementation(project(":piikii-application"))

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-aop")

        // for docs
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

        // for validation
        implementation("org.springframework.boot:spring-boot-starter-validation")
    }
}

allprojects {
    tasks.bootJar {
        enabled = false
    }

    tasks.jar {
        enabled = true
    }
}
