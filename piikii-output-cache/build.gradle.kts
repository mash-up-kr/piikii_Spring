project(":piikii-output-cache:redis") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
    }
}

project(":piikii-output-cache:caffeine") {
    dependencies {
        implementation("com.github.ben-manes.caffeine:caffeine")
    }
}

allprojects {
    tasks.bootJar {
        enabled = false
    }

    tasks.jar {
        enabled = true
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-cache")
    }
}