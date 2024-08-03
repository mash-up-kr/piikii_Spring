dependencies {
    implementation(project(":piikii-input-http"))
    implementation(project(":piikii-output-eventbroker:kafka"))
    implementation(project(":piikii-output-persistence:postgresql"))
    implementation(project(":piikii-output-cache:redis"))
    implementation(project(":piikii-output-cache:caffeine"))
    implementation(project(":piikii-output-storage:ncp"))
    implementation(project(":piikii-output-web:avocado"))
    implementation(project(":piikii-output-web:lemon"))
    implementation(project(":piikii-output-web:tmap"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(project(":piikii-application"))
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("${System.getenv("NCP_CONTAINER_REGISTRY")}/piikii")
    environment.set(
        mapOf(
            "BP_JVM_VERSION" to "21",
            "BPE_SPRING_PROFILES_ACTIVE" to "prod",
            "BPE_JAVA_TOOL_OPTIONS" to
                buildString {
                    // G1 GC
                    append("-XX:+UseG1GC ")
                    // Container JVM
                    append("-XX:+UseContainerSupport ")
                    // Heap 메모리 설정 2G
                    append("-Xms2g -Xmx2g ")
                    // OOM시 Heap Dump
                    append("-XX:+HeapDumpOnOutOfMemoryError ")
                    // OOM시 Heap Dump 로그 생성 경로 (파일 이름 : 발생한 시각)
                    append("-XX:HeapDumpPath=/root/heapDump/%Y%m%d_%H%M%S.hprof ")
                    // 중복 문자열 제거로 메모리 절약
                    append("-XX:+UseStringDeduplication ")
                    // OOM시 애플리케이션 즉시 종료
                    append("-XX:+ExitOnOutOfMemoryError ")
                    // Encoding
                    append("-Dfile.encoding=UTF-8")
                },
        ),
    )
    docker {
        publishRegistry {
            url.set(System.getenv("NCP_CONTAINER_REGISTRY"))
            username.set(System.getenv("NCP_API_ACCESS_KEY"))
            password.set(System.getenv("NCP_API_SECRET_KEY"))
        }
    }
    publish.set(true)
}

tasks.bootJar {
    enabled = true
}
