dependencies {
    implementation(project(":piikii-input-http"))
    implementation(project(":piikii-output-eventbroker:kafka"))
    implementation(project(":piikii-output-persistence:postgresql"))
    implementation(project(":piikii-output-cache:redis"))
    implementation(project(":piikii-output-cache:caffeine"))
    implementation(project(":piikii-output-storage:ncp"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("${System.getenv("NCP_CONTAINER_REGISTRY")}/piikii")
    environment.set(
        mapOf(
            "BP_JVM_VERSION" to "21",
            "BPE_SPRING_PROFILES_ACTIVE" to "prod",
            "BPE_JAVA_TOOL_OPTIONS" to "-XX:+UseG1GC -XX:+UseContainerSupport",
        ),
    )
    docker {
        publishRegistry {
            url.set(System.getenv("NCP_CONTAINER_REGISTRY"))
            username.set(System.getenv("NCP_API_ACCESS_KEY"))
            password.set(System.getenv("NCP_API_SECRET_KEY"))
        }
    }
    publish.set(true)
}

tasks.bootJar {
    enabled = true
}
