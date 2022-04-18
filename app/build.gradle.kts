plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.5.31"
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = Config.testInstrumentationRunner

        buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
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

    buildFeatures {
        dataBinding = true
        viewBinding = true
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

ktlint {
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Kotlin.stdlib)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.coreKtx)
    implementation(View.constraintLayout)
    implementation(View.swipeRefresh)

    // Material Design
    implementation(View.material)

    // Room
    implementation(Database.roomRuntime)
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

    // Lifecycle KTX
    implementation(AndroidX.viewModel)
    implementation(AndroidX.liveData)
    implementation(AndroidX.lifeCycleCommon)

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
    testImplementation(AndroidX.archCoreTesting)
    testImplementation(UnitTest.junit)
    testImplementation(UnitTest.roboelectric)
    testImplementation(UnitTest.hamcrest)
    testImplementation(Kotlin.coroutineTest)
    testImplementation(UnitTest.mockk)

    // AndroidX Test - Instrumented testing
    androidTestImplementation(UnitTest.mockk)
    androidTestImplementation(AndroidTest.espresso)
    androidTestImplementation(AndroidTest.espressoContrib)
    androidTestImplementation(AndroidTest.espressoIntent)
    androidTestImplementation(AndroidX.archCoreTesting)
    androidTestImplementation(Kotlin.coroutineTest)
}
