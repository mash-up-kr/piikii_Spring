plugins {
    id("piikii-convention")
}

dependencies {
    implementation(project(":piikii-application"))
    implementation(libs.bundles.adaptor.cache.redis)

    // redisson
    implementation("org.redisson:redisson-spring-boot-starter:3.27.0")
}
