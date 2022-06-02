plugins {
  kotlin("multiplatform")
  id("maven-publish")
}

configureMppDefaults()

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        api(okio())
        api(groovy.util.Eval.x(project, "x.dep.uuid"))
        api(projects.apolloAnnotations)
      }
    }
  }
}

val jvmJar by tasks.getting(Jar::class) {
  manifest {
    attributes("Automatic-Module-Name" to "com.apollographql.apollo3.api")
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