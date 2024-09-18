plugins {
    id("piikii-convention")
}

dependencies {
    implementation(project(":piikii-application"))
    implementation(libs.bundles.adaptor.cache.redis)
}
