plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies{
    api("com.squareup.retrofit2:retrofit:3.0.0")
    api("com.squareup.retrofit2:converter-gson:3.0.0")

    api(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    api("com.squareup.okhttp3:okhttp")
    api("com.squareup.okhttp3:logging-interceptor")

    implementation(group = "javax.inject", name = "javax.inject", version = "1")

    implementation(project(":domain"))
}