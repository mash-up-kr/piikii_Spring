project(":piikii-output-persistence:mongodb") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    }
}

project(":piikii-output-persistence:mysql") {
    dependencies {
        implementation("mysql:mysql-connector-java:8.0.32")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
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
