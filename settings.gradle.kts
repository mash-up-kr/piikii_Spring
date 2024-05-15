plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "mmz"

include("mmz-api")
include("mmz-api:admin")
include("mmz-api:client")
include("mmz-api:client:member")
include("mmz-api:client:post")

include("mmz-infrastructure")
include("mmz-infrastructure:dbms")
include("mmz-infrastructure:dbms:mysql")
include("mmz-infrastructure:dbms:mongodb")
include("mmz-infrastructure:messagingsystem")
include("mmz-infrastructure:messagingsystem:kafka")
include("mmz-infrastructure:cache")
include("mmz-infrastructure:cache:redis")
include("mmz-infrastructure:cache:caffeine")

include("mmz-core")
include("mmz-core:auth")
include("mmz-core:secure")
include("mmz-core:exception")
include("mmz-core:member")
include("mmz-core:post")

include("mmz-member")
include("mmz-post")

