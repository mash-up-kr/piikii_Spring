dependencies {
    implementation(project(":piikii-input-http"))
    implementation(project(":piikii-output-eventbroker:kafka"))
    implementation(project(":piikii-output-persistence:postgresql"))
    implementation(project(":piikii-output-cache:redis"))
    implementation(project(":piikii-output-cache:caffeine"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("mashupmz/piikii")
    environment.set(mapOf("BP_JVM_VERSION" to "21"))
    docker {
        publishRegistry {
            username.set(System.getenv("DOCKER_HUB_USERNAME"))
            password.set(System.getenv("DOCKER_HUB_PASSWORD"))
            url.set(System.getenv("DOCKER_HUB_REPOSITORY"))
        }
    }
    publish.set(true)
}
