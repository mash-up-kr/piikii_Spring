plugins {
    id("piikii-convention")
}

dependencies {
    implementation(project(":piikii-application"))
    implementation(libs.spring.web)
    implementation(libs.spring.boot.starter.redisson)
}
