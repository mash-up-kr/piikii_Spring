pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "piikii"

include("piikii-application")
include("piikii-bootstrap")
include("piikii-common")
include("piikii-input-http")
include("piikii-output-cache:redis")
include("piikii-output-cache:caffeine")
include("piikii-output-eventbroker:kafka")
include("piikii-output-persistence:postgresql")
include("piikii-output-storage:ncp")
include("piikii-output-web:avocado")
include("piikii-output-web:lemon")
include("piikii-output-web:tmap")
