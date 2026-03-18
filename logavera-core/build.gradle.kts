plugins {
    id("java")
}

group = "com.logavera"
version = "1.0"

repositories {
    mavenCentral()
}

val jakarta = "3.0.0"
val jUnit = "5.10.0"
val mockitoJUnitJupiter = "5.22.0"
val jUnitUpiter = "6.1.0-M1"

dependencies {
    implementation("jakarta.annotation:jakarta.annotation-api:$jakarta")
    testImplementation(platform("org.junit:junit-bom:$jUnit"))
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitUpiter")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoJUnitJupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$jUnitUpiter")
}

tasks.test {
    useJUnitPlatform()
}