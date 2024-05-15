project(":mmz-member") {
    dependencies {
        api(project(":mmz-infrastructure:dbms:mysql"))
        api(project(":mmz-infrastructure:dbms:mongodb"))
        api(project(":mmz-infrastructure:messagingsystem:kafka"))
    }
}