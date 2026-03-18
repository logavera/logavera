plugins {
    java
    id("org.danilopianini.publish-on-central") version "9.1.11"
}

group = "com.logavera"
version = "1.0.0"

tasks.withType<PublishToMavenRepository>().configureEach {
    onlyIf { project.subprojects.contains(project) }
}

subprojects {

    apply(plugin = "java")
    apply(plugin = "signing")
    apply(plugin = "org.danilopianini.publish-on-central")

    java {
        withSourcesJar()
        withJavadocJar()
    }

    publishOnCentral {
        repoOwner.set("logavera")
        projectDescription.set("Tamper-proof consent logging library for Java")
        projectLongName.set(project.name)
        licenseName.set("GNU Affero General Public License v3.0")
        licenseUrl.set("https://www.gnu.org/licenses/agpl-3.0.en.html")
        projectUrl.set("https://github.com/logavera/logavera")
        scmConnection.set("scm:git:https://github.com/logavera/logavera.git")
    }

    publishing {
        publications.withType<MavenPublication>().configureEach {
            pom {
                developers {
                    developer {
                        name.set("Logavera")
                        email.set("consentlogavera@gmail.com")
                        url.set("https://www.logavera.com/")
                    }
                }
            }
        }
    }

    signing {
        val signingKey: String? by project
        val signingPassword: String? by project

        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}

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

                pom {
                    name.set("Logavera")
                    description.set("Tamper-proof consent logging library for Java")
                    url.set("https://github.com/logavera/logavera")

                    licenses {
                        license {
                            name.set("GNU Affero General Public License v3.0")
                            url.set("https://www.gnu.org/licenses/agpl-3.0.en.html")
                        }
                    }

                    developers {
                        developer {
                            id.set("logavera")
                            name.set("logavera")
                        }
                    }

                    scm {
                        url.set("https://github.com/logavera/logavera")
                        connection.set("scm:git:git://github.com/logavera/logavera.git")
                        developerConnection.set("scm:git:ssh://github.com/logavera/logavera.git")
                    }
                }
            }
        }
        repositories {
            maven {
                name = "sonatype"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

                credentials {
                    username = System.getenv("OSSRH_USERNAME")
                    password = System.getenv("OSSRH_PASSWORD")
                }
            }
        }
    }
}