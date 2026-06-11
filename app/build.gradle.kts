import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if(localPropertiesFile.exists()){
    localPropertiesFile.inputStream().use {
        localProperties.load(it)
    }
}

val pexelsApiKey = localProperties.getProperty("PEXELS_API_KEY") ?: ""

android {
    namespace = "ch.heigvd.iict.mvp.snapeat"  // ← corrigé
    compileSdk = 34

    defaultConfig {
        applicationId = "ch.heigvd.iict.mvp.snapeat"  // ← corrigé
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "PEXELS_API_KEY",
            "\"$pexelsApiKey\""
        )
    }

    lint {
        disable += "AutoboxingStateCreation"
        disable += "MutableCollectionMutableState"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // ← ajout obligatoire pour Compose
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:34.14.0"))

    // Add the dependency for the Firebase AI Logic library When using the BoM,
    // you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-ai")

    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.activity:activity-compose")

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    implementation("com.google.android.material:material:1.12.0")

    // JSON
    implementation("com.google.code.gson:gson:2.11.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.4")

    // Image Loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
}