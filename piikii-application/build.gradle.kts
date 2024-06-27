dependencies {
    // for docs
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
