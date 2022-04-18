import org.gradle.api.JavaVersion

object Config {
  const val minSdkVersion = 26
  const val compileSdkVersion = 30
  const val targetSdkVersion = 30
  const val versionName = "1.0"
  const val versionCode = 1
  val javaVersion = JavaVersion.VERSION_11
  const val buildTools = "30.0.3"
  const val applicationId = "com.metis.rickmorty"
  const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

/**Helps handle group of libraries**/
interface Libraries {
  val components: List<String>
}

object Plugins {
  object Version {
    const val gradleAndroidVersion = "7.0.2"
  }

  const val gradleAndroid = "com.android.tools.build:gradle:${Version.gradleAndroidVersion}"
  const val kotlinGradlePlugin =
    "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.Versions.kotlin}"
  const val safeArgs =
    "androidx.navigation:navigation-safe-args-gradle-plugin:${Navigation.Versions.navigation}"
}

object Kotlin {

  object Versions {
    const val kotlin = "1.6.10"
    const val coroutines = "1.4.2"
    const val kotlinxJson = "1.3.2"
  }

  const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
  const val coroutinesAndroid =
    "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
  const val coroutineTest =
    "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
  const val kotlinxSerialization =
    "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxJson}"
}

object AndroidX : Libraries {
  private object Versions {
    const val androidx_core = "1.3.2"
    const val appCompat = "1.2.0"
    const val lifeCycle = "2.3.0-alpha03"
    const val preferences = "1.1.1"

    const val archCoreTesting = "2.1.0"
    const val testExt = "1.1.2"
    const val testRules = "1.3.0"
  }

  const val coreKtx = "androidx.core:core-ktx:${Versions.androidx_core}"
  const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
  const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}"
  const val viewModel =
    "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}"
  const val lifeCycleCommon =
    "androidx.lifecycle:lifecycle-common-java8:${Versions.lifeCycle}"
  const val preferences = "androidx.preference:preference-ktx:${Versions.preferences}"

  const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
  const val testExt = "androidx.test.ext:junit-ktx:${Versions.testExt}"

  override val components: List<String>
    get() = listOf(coreKtx, viewModel, appCompat, lifeCycleCommon, liveData, preferences)
}

object Dagger : Libraries {

  private object Versions {
    const val hilt = "2.41"
  }

  const val daggerHilt = "com.google.dagger:hilt-android:${Versions.hilt}"
  const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
  const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

  override val components: List<String> = listOf(daggerHilt, hiltCompiler)
}

object Network : Libraries {

  private object Versions {
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.0"
    const val chuck = "1.1.0"
    const val gson = "2.8.6"
  }

  const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
  const val chuck = "com.readystatesoftware.chuck:library:${Versions.chuck}"
  const val chuckNoOp = "com.readystatesoftware.chuck:library-no-op:${Versions.chuck}"
  const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
  const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
  const val gson = "com.google.code.gson:gson:${Versions.gson}"

  override val components: List<String>
    get() = listOf(retrofit, okhttp, okhttpInterceptor, gson)
}

object Database : Libraries {

  object Versions {
    const val room = "2.4.0-alpha04"
  }

  const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
  const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
  const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

  override val components: List<String>
    get() = listOf(roomRuntime, roomKtx)
}

object Navigation : Libraries {

  object Versions {
    const val navigation = "2.4.0"
  }

  const val navigationFragment =
    "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
  const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

  override val components: List<String>
    get() = listOf(navigationFragment, navigationUi)
}

object View : Libraries {

  private object Versions {
    const val material = "1.3.0-alpha04"
    const val recyclerView = "1.2.0-beta01"
    const val constraintLayout = "2.0.4"
    const val swipeRefresh = "1.1.0"
    const val viewPager = "1.0.0"
  }

  const val material = "com.google.android.material:material:${Versions.material}"
  const val constraintLayout =
    "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
  const val viewPager = "androidx.viewpager2:viewpager2:${Versions.viewPager}"
  const val swipeRefresh =
    "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
  const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

  override val components = listOf(
    material, constraintLayout, viewPager, swipeRefresh,
    recyclerView
  )
}

object Utils : Libraries {

  private object Versions {
    const val timber = "4.7.1"
    const val picasso = "2.71828"
  }

  const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
  const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
  override val components: List<String>
    get() = listOf(timber, picasso)
}

object UnitTest : Libraries {
  private object Versions {
    const val junit = "4.13.2"
    const val mockk = "1.12.2"
    const val hamcrest = "1.3"
    const val roboelectric = "4.7.3"
  }

  const val junit = "junit:junit:${Versions.junit}"
  const val roomTest = "androidx.room:room-testing:${Database.Versions.room}"
  const val mockk = "io.mockk:mockk:${Versions.mockk}"
  const val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"
  const val roboelectric = "org.robolectric:robolectric:${Versions.roboelectric}"

  override val components: List<String>
    get() = listOf(junit)
}

object AndroidTest : Libraries {
  private object Versions {
    const val espresso = "3.3.0"
    const val junitExt = "1.1.2"
  }

  const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
  const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
  const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
  const val espressoIntent = "androidx.test.espresso:espresso-intents:${Versions.espresso}"

  override val components: List<String>
    get() = listOf(espresso, junitExt)
}
