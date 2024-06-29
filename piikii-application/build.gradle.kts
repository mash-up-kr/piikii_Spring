dependencies {
    // for docs
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // for transaction
    implementation("org.springframework:spring-tx:6.1.10")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
