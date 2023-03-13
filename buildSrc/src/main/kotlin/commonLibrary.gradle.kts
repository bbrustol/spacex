import Dependencies.common
import Dependencies.commonUnitTest
import gradle.kotlin.dsl.accessors._5a0acc637377881228018c78f84444a8.kotlin
import gradle.kotlin.dsl.accessors._5a0acc637377881228018c78f84444a8.sourceSets

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = Versions.App.COMPILE_SDK
}

dependencies {
    common()
    commonUnitTest()
}