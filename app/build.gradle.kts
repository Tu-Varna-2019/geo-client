plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  id("org.sonarqube") version "4.4.1.3373"
  kotlin("kapt")
  id("com.google.dagger.hilt.android")
  id("kotlin-parcelize")
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
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
  implementation("androidx.compose.material:material:1.0.5")

  // Datastore preferences
  implementation("androidx.datastore:datastore-preferences-core:1.1.0")
  implementation("androidx.datastore:datastore-preferences:1.1.0")

  // Google maps API deps
  implementation("com.google.android.gms:play-services-location:18.0.0")
  implementation("com.google.android.gms:play-services-maps:17.0.1")
  implementation("com.google.maps.android:android-maps-utils:1.1.0")
  implementation("com.google.maps.android:maps-compose:4.4.1")

  testImplementation("io.kotest:kotest-framework-api-jvm:4.6.0")
  testImplementation("io.kotest:kotest-property:5.8.1")
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
