project(":piikii-common") {
    // for RestClient
    dependencies {
        implementation("org.springframework:spring-web")
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
