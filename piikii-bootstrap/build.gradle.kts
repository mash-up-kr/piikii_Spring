dependencies {
    implementation(project(":piikii-input-http"))
    implementation(project(":piikii-output-eventbroker:kafka"))
    implementation(project(":piikii-output-persistence:postgresql"))
}

application {
    mainClass.set("mashup.mmz.bootstrap.BootstrapApplicationKt")
}
