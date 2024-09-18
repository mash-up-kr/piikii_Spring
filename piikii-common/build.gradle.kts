plugins {
    id("piikii-convention")
}

dependencies {
    // for RestClient
    dependencies {
        implementation("org.springframework:spring-web") {
            exclude("org.springframework:spring-beans")
            exclude("org.springframework:spring-core")
            exclude("org.springframework:spring-aop")
            exclude("org.springframework:spring-context")
        }
    }
}


