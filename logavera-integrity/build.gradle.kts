plugins {
    id("java")
}

group = "com.logavera"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":logavera-core"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}