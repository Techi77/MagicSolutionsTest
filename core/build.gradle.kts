plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.core"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    api(project(mapOf("path" to ":mvi")))

    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.runtime.compose)
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.viewmodel.compose)

    api(platform(libs.compose.bom))
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material3)

    androidTestApi(platform(libs.compose.bom))
    androidTestApi(libs.ui.test.junit4)
    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)

    api(libs.activity.compose)
    api(libs.navigation.compose)
    api(libs.coil.compose)
    api(libs.coil.video)
    api(libs.lottie.compose)
    api(libs.haze)
//    api(libs.constraintlayout.compose)

    api(libs.koin.core)
    api(libs.koin.android)
    api(libs.koin.compose)
}