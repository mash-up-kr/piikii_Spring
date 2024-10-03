plugins {
    id("piikii-convention")
}

dependencies {
    implementation(project(":piikii-application"))
    implementation(project(":piikii-output-cache:redis"))
    implementation(libs.bundles.adaptor.input.http)
}
