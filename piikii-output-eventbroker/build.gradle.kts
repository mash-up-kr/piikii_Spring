project(":piikii-output-eventbroker:kafka") {
    dependencies {
        implementation("org.springframework.kafka:spring-kafka")
    }
}

allprojects {
    dependencies {
        implementation(project(":piikii-application"))
    }

    tasks.bootJar {
        enabled = false
    }

    tasks.jar {
        enabled = true
    }
}
