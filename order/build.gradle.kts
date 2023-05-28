dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.axonframework:axon-spring-boot-starter:4.5.14")

    implementation(project(":common"))

    runtimeOnly("com.mysql:mysql-connector-j")
}
