plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "mmz"

include("piikii-application")

include("piikii-bootstrap")

include("piikii-common")
include("piikii-common:exception")

include("piikii-input-http")

include("piikii-output-cache")
include("piikii-output-cache:redis")
include("piikii-output-cache:caffeine")

include("piikii-output-eventbroker")
include("piikii-output-eventbroker:kafka")

include("piikii-output-persistence")
include("piikii-output-persistence:postgresql")