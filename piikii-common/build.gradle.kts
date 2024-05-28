project(":piikii-common") {}

allprojects {
    tasks.bootJar {
        enabled = false
    }

    tasks.jar {
        enabled = true
    }
}
