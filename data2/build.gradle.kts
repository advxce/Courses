plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.data2"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api("com.squareup.retrofit2:retrofit:3.0.0")
    api("com.squareup.retrofit2:converter-gson:3.0.0")

    api(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    api("com.squareup.okhttp3:okhttp")
    api("com.squareup.okhttp3:logging-interceptor")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation(group = "javax.inject", name = "javax.inject", version = "1")
    val room_version = "2.8.1"

    api("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    api("androidx.room:room-ktx:$room_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")



    implementation(project(":domain"))
}