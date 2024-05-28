project(":piikii-output-persistence:postgresql") {
    dependencies {
        implementation(project(":piikii-common"))
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.postgresql:postgresql")
    }

    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
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
