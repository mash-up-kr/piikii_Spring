project(":piikii-output-web:avocado") {
    dependencies {
    }
}

project(":piikii-output-web:lemon") {
    dependencies {
    }
}

allprojects {
    dependencies {
        implementation("org.springframework:spring-web")
        implementation(project(":piikii-application"))
    }

    tasks.bootJar {
        enabled = false
    }

    tasks.jar {
        enabled = true
    }
}
