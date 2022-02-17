import java.io.FileInputStream
import java.util.Properties

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  kotlin("plugin.serialization") version "1.5.31"
  id("kotlin-parcelize")
  id("androidx.navigation.safeargs")
  id("dagger.hilt.android.plugin")
}

android {
  compileSdk = Config.compileSdkVersion
  buildToolsVersion = Config.buildTools
  if (project.hasProperty("keystore.properties")) {
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    signingConfigs {
      getByName("debug") {
        keyAlias = keystoreProperties["keyAlias"].toString()
        keyPassword = keystoreProperties["keyPassword"].toString()
        storeFile = file(rootDir.absolutePath + keystoreProperties["storeFile"])
        storePassword = keystoreProperties["storePassword"].toString()
      }
      create("release") {
        keyAlias = keystoreProperties["keyAlias"].toString()
        keyPassword = keystoreProperties["keyPassword"].toString()
        storeFile = file(rootDir.absolutePath + keystoreProperties["storeFile"])
        storePassword = keystoreProperties["storePassword"].toString()
      }
    }
  }

  defaultConfig {
    applicationId = Config.applicationId
    minSdk = Config.minSdkVersion
    targetSdk = Config.targetSdkVersion
    versionCode = Config.versionCode
    versionName = Config.versionName
    testInstrumentationRunner = Config.testInstrumentationRunner

    buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")

    kapt {
      arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
      }
      correctErrorTypes = true
    }
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
      if (project.hasProperty("keystore.properties")) {
        signingConfig = signingConfigs.getByName("release")
      }
      isDebuggable = false
    }

    getByName("debug") {
      if (project.hasProperty("keystore.properties")) {
        signingConfig = signingConfigs.getByName("debug")
      }
      isDebuggable = true
    }
  }

  android {
    sourceSets {
      getByName("test").java.srcDir("src/sharedTest/java")
      getByName("androidTest").java.srcDir("src/sharedTest/java")
    }
  }

  hilt {
    enableAggregatingTask = true
  }

  buildFeatures {
    dataBinding = true
    viewBinding = true
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.0.5"
  }

  testOptions {
    unitTests.apply {
      isReturnDefaultValues = true
      isIncludeAndroidResources = true
    }
  }

  compileOptions {
    sourceCompatibility(Config.javaVersion)
    targetCompatibility(Config.javaVersion)
  }

  tasks.withType().all {
    kotlinOptions {
      jvmTarget = Config.javaVersion.toString()
      // allWarningsAsErrors = true
      freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
  }

  packagingOptions {
    resources.excludes.add("**/attach_hotspot_windows.dll")
    resources.excludes.add("META-INF/licenses/**")
    resources.excludes.add("META-INF/AL2.0")
    resources.excludes.add("META-INF/LGPL2.1")
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(Kotlin.stdlib)
  implementation(AndroidX.appCompat)
  implementation(AndroidX.coreKtx)
  implementation(View.constraintLayout)
  implementation(AndroidX.legacySupport)

  // Material Design
  implementation(View.material)

  // Jetpack Compose
  implementation(Compose.activity)
  implementation(Compose.material)
  implementation(Compose.animation)
  implementation(Compose.tooling)
  implementation(Compose.appCompat)
  implementation(Compose.paging)

  debugImplementation("androidx.compose.ui:ui-tooling:1.0.5")
  implementation("androidx.compose.ui:ui-tooling-preview:1.0.5")

  implementation("com.google.accompanist:accompanist-swiperefresh:0.15.0")

  // Room
  implementation(Database.roomRuntime)
  implementation("androidx.legacy:legacy-support-v4:1.0.0")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
  kapt(Database.roomCompiler)
  implementation(Database.roomKtx)

  // Kotlin Coroutines
  implementation(Kotlin.coroutinesAndroid)
  implementation(Kotlin.kotlinxSerialization)

  // Navigation Components
  implementation(Navigation.navigationFragment)
  implementation(Navigation.navigationUi)

  // Retrofit
  implementation(Network.retrofit)
  implementation(Network.gsonConverter)
  implementation(Network.gson)

  // Preferences
  implementation(AndroidX.preferences)

  // Timber
  implementation(Utils.timber)

  // Google Play Services
  implementation(Google.googlePlayGms)

  // Lifecycle KTX
  implementation(AndroidX.viewModel)
  implementation(AndroidX.liveData)
  implementation(AndroidX.lifeCycleCommon)

  // Paging Library
  implementation(AndroidX.paging)

  // WorkManager
  implementation(AndroidX.workManager)

  // Dagger-Hilt
  implementation(Dagger.daggerHilt)
  kapt(Dagger.hiltCompiler)

  // OKHttp Logging Interceptor
  implementation(Network.okhttpInterceptor)

  // picasso
  implementation(Utils.picasso)

  // Chuck
  debugImplementation(Network.chuck)
  releaseImplementation(Network.chuckNoOp)

  // AndroidX Test - JVM testing
  testImplementation(AndroidX.testExt)
  testImplementation(AndroidX.coreKtxTest)
  testImplementation(AndroidX.archCoreTesting)
  testImplementation(UnitTest.junit)
  testImplementation(UnitTest.roboelectric)
  testImplementation(UnitTest.hamcrest)
  testImplementation(Kotlin.coroutineTest)
  testImplementation(UnitTest.mockk)

  // AndroidX Test - Instrumented testing
  androidTestImplementation(UnitTest.mockk)
  androidTestImplementation(AndroidX.testExt)
  androidTestImplementation(AndroidTest.espresso)
  androidTestImplementation(AndroidTest.espressoContrib)
  androidTestImplementation(AndroidTest.espressoIntent)
  androidTestImplementation(AndroidX.archCoreTesting)
  androidTestImplementation(AndroidX.coreKtxTest)
  androidTestImplementation(AndroidX.testRules)
  androidTestImplementation(Kotlin.coroutineTest)

  // Until the bug at https://issuetracker.google.com/128612536 is fixed
  debugImplementation(AndroidX.fragmentTesting)
  implementation(AndroidTest.idlingResource)
}
