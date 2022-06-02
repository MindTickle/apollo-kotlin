plugins {
  kotlin("multiplatform")
  id("maven-publish")
}

configureMppDefaults(withLinux = false)

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        api(projects.apolloApi)
        api(groovy.util.Eval.x(project, "x.dep.kotlinxdatetime"))
      }
    }
    val jsMain by getting {
      dependencies {
        implementation(npm("big.js", "5.2.2"))
      }
    }
  }
}

val jvmJar by tasks.getting(Jar::class) {
  manifest {
    attributes("Automatic-Module-Name" to "com.apollographql.apollo3.adapter")
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
}