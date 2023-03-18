import Dependencies.common
import Dependencies.commonView
import Dependencies.commonIntegrationTest
import Dependencies.commonUnitTest
import gradle.kotlin.dsl.accessors._5a0acc637377881228018c78f84444a8.kapt

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = Versions.App.COMPILE_SDK
    defaultConfig {
        minSdk = Versions.App.MIN_SDK
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.AndroidX.COMPOSE_COMPILER
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    packagingOptions {
        resources {
            resources.excludes.add("META-INF/*")
        }
    }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    common()
    commonView()
    commonUnitTest()
    commonIntegrationTest()
}