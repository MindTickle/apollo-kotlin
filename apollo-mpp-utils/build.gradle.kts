plugins {
  kotlin("multiplatform")
  id("maven-publish")
}

configureMppDefaults(withLinux = false)

kotlin {
  sourceSets {
  }
}

val jvmJar by tasks.getting(Jar::class) {
  manifest {
    attributes("Automatic-Module-Name" to "com.apollographql.apollo3.mpp")
  }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
  kotlinOptions {
    allWarningsAsErrors = true
  }
}

publishing {
  publications {
    repositories {
      maven {
        url = uri("https://maven.pkg.github.com/MindTickle/apollo-kotlin")
        credentials {
          username = System.getenv("GITHUB_ACTOR")
          password = System.getenv("GITHUB_TOKEN")
        }
      }
    }
  }
}