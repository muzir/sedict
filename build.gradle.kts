plugins {
    id("java")
    id("application")
}

group = "org.stackoverflowdata"
version = "0.1"


repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.6.0")


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.7.0")

}

application {
    // Specify the main class
    mainClass.set("org.stackoverflowdata.loader.postgres.Main")
}

tasks.test {
    useJUnitPlatform()
}