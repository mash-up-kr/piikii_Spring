project(":mmz-api:client:member") {
    dependencies {
        api(project(":mmz-core:member"))

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-aop")
    }
}

project(":mmz-api:client:post") {
    dependencies {
        api(project(":mmz-core:post"))

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-aop")
    }
}