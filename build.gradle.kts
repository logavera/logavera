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
