project(":piikii-common:exception") {}

allprojects {
    tasks.bootJar {
        enabled = false
    }

    tasks.jar {
        enabled = true
    }
}