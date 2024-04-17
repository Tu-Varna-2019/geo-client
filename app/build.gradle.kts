plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  id("org.sonarqube") version "4.4.1.3373"
  kotlin("kapt")
  id("com.google.dagger.hilt.android")
  id("kotlin-parcelize")
}

sonar {
  properties {
    property(
      "sonar.projectKey",
      "Tu-Varna-2019_Masters-Summer-Project-IMD-KotlinAndroid-Client-Geo-System_AY4Jh2hVYmNxNLY6TIyH",
    )
  }
}

kapt { correctErrorTypes = true }

android {
  namespace = "com.tuvarna.geo"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.tuvarna.geo"
    minSdk = 31
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables { useSupportLibrary = true }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions { jvmTarget = "1.8" }
  buildFeatures { compose = true }
  composeOptions { kotlinCompilerExtensionVersion = "1.5.1" }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.navigation.compose)

    // for maps
    // Jetpack Compose
    implementation ("androidx.compose.ui:ui:1.0.5")
    implementation ("androidx.compose.material:material:1.0.5")
    implementation ("androidx.compose.ui:ui-tooling:1.0.5")
    implementation ("androidx.compose.runtime:runtime-livedata:1.0.5")
    implementation ("androidx.compose.runtime:runtime-rxjava2:1.0.5")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    implementation ("androidx.activity:activity-compose:1.3.1")

    // Google Maps
    implementation ("com.google.android.gms:play-services-maps:17.0.1")

    // Location services
    implementation ("com.google.android.gms:play-services-location:18.0.0")

    // AndroidX Core
    implementation ("androidx.core:core-ktx:1.7.0")

    // AndroidX AppCompat
    implementation ("androidx.appcompat:appcompat:1.4.0")

    // AndroidX Activity Compose
    implementation ("androidx.activity:activity-compose:1.4.0")
    //

  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("com.squareup.moshi:moshi:1.14.0")
  implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
  implementation("io.kotest:kotest-framework-engine:4.6.0")
  implementation("com.google.dagger:hilt-android:2.51")
  implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
  implementation("com.jakewharton.timber:timber:5.0.1")
  kapt("com.google.dagger:hilt-android-compiler:2.51")
  implementation(libs.androidx.appcompat)

  testImplementation("io.kotest:kotest-framework-api-jvm:4.6.0")
  testImplementation("io.kotest:kotest-property:4.6.0")
  testImplementation("io.kotest:kotest-runner-junit5:4.6.0")
  testImplementation("io.kotest:kotest-assertions-core:4.6.0")
  testImplementation("io.kotest:kotest-framework-engine:4.6.0")

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}
