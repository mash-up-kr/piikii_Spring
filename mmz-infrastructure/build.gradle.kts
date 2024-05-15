project(":mmz-infrastructure:dbms:mysql") {
    dependencies {
        implementation("mysql:mysql-connector-java")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    }

    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
    }

}

project(":mmz-infrastructure:dbms:mongodb") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    }
}

project(":mmz-infrastructure:messagingsystem:kafka") {
    dependencies {
        implementation("org.springframework.kafka:spring-kafka")
    }
}