// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath(Plugins.kotlinGradlePlugin)
    classpath(Plugins.gradleAndroid)
    classpath(Plugins.safeArgs)
    classpath(Dagger.hiltGradlePlugin)
    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

plugins {
  id("org.jetbrains.kotlin.android") version "1.5.31" apply false
}

allprojects {
  repositories {
    google()
    mavenCentral()
    maven(url = "https://jitpack.io")
  }
}

tasks.register("clean").configure {
  delete("build")
}
