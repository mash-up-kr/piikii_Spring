project(":piikii-output-web:avocado") {
    dependencies {
        implementation("org.springframework:spring-web")
    }
}

project(":piikii-output-web:lemon") {
    dependencies {
        implementation("org.springframework:spring-web")
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
