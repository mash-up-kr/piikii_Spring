dependencies {
    implementation(project(":piikii-input-http"))
    implementation(project(":piikii-output-eventbroker:kafka"))
    implementation(project(":piikii-output-persistence:postgresql"))
    implementation(project(":piikii-output-cache:redis"))
    implementation(project(":piikii-output-cache:caffeine"))
}

application {
    mainClass.set("com.piikii.bootstrap.PiikiiBootstrapApplication")
}
