dependencies {
    implementation(project(":piikii-input-http"))
    implementation("org.springframework.boot:spring-boot-autoconfigure")
}

application {
    mainClass.set("mashup.mmz.bootstrap.BootstrapApplicationKt")
}
