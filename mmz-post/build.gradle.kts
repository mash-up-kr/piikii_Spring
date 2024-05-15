project(":mmz-core:post") {
    dependencies {
        api(project(":mmz-infrastructure:dbms:mysql"))
        api(project(":mmz-infrastructure:dbms:mongodb"))
        api(project(":mmz-infrastructure:messagingsystem:kafka"))
    }
}