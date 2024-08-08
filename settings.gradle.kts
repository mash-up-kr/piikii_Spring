plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
    id("com.gradle.develocity") version ("3.17.6")
}

rootProject.name = "piikii"

include("piikii-application")
include("piikii-bootstrap")
include("piikii-common")
include("piikii-input-http")
include("piikii-output-cache")
include("piikii-output-cache:redis")
include("piikii-output-cache:caffeine")
include("piikii-output-eventbroker")
include("piikii-output-eventbroker:kafka")
include("piikii-output-persistence")
include("piikii-output-persistence:postgresql")
include("piikii-output-storage")
include("piikii-output-storage:ncp")
include("piikii-output-web")
include("piikii-output-web:avocado")
include("piikii-output-web:lemon")
include("piikii-output-web:tmap")
