plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}

extra["javaVersion"] = JavaVersion.VERSION_17
extra["jvmTarget"] = "17"