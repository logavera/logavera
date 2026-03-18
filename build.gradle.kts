plugins {
    `maven-publish`
}

subprojects {

    group = "com.logavera"
    version = "1.0.0"

    apply(plugin = "java-library")
    //apply(plugin = "maven-publish")

/*    java {
        withSourcesJar()
        withJavadocJar()

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }*/

/*    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }
    }*/
}