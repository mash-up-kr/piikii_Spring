plugins {
    id("piikii-convention")
}

dependencies {
    implementation(project(":piikii-application"))
    implementation("org.springframework:spring-web")
}
