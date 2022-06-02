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
        api(projects.apolloNormalizedCacheApi)
        api(projects.apolloRuntime)
        api(projects.apolloMockserver)
        api(groovy.util.Eval.x(project, "x.dep.kotlin.coroutines"))
      }
    }

    val jvmTest by getting {
      dependencies {
        implementation(groovy.util.Eval.x(project, "x.dep.truth"))
      }
    }

    val jsMain by getting {
      dependencies {
        implementation(groovy.util.Eval.x(project, "x.dep.kotlin.nodejs"))
        implementation(kotlin("test-js"))
        api(groovy.util.Eval.x(project, "x.dep.okioNodeJs"))
      }
    }
    val jsTest by getting {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
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
