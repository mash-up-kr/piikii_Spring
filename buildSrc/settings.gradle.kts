rootProject.name = "piikii-convention"

plugins {
    id("com.gradle.develocity") version ("3.17.6")
}

develocity {
    buildScan {
        projectId.set("piikii-application")

        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")

        link("VCS", "https://github.com/mash-up-kr/piikii_Spring/tree/develop")
        value("hash", System.getenv("CURRENT_GIT_SHA"))
        value("status", System.getenv("STATUS"))

        buildFinished {
            if (this.failures.isNotEmpty()) {
                val failureMessages = this.failures.mapNotNull { it.message }.joinToString(",") { it }
                value("Failed with", failureMessages)
            }
        }
        // default false option: 추후 많은 빌드로 인해 이슈가 생길 때를 대비
        // publishing.onlyIf { false }
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}


