import org.gradle.kotlin.dsl.DependencyHandlerScope

object Dependencies {

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Versions.Google.MATERIAL}"
        object Hilt {
            const val ANDROID = "com.google.dagger:hilt-android:${Versions.Google.HILT}"
            const val ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.Google.HILT}"
        }
    }

    object AndroidX {
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.AndroidX.CORE_KTX}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.AndroidX.APPCOMPAT}"
        const val LIFECYCLE ="androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.LIFECYCLE}"
        const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Versions.AndroidX.ACTIVITY_COMPOSE}"
        const val FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.AndroidX.FRAGMENT}"

        object Compose {
            // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
            const val FOUNDATION = "androidx.compose.foundation:foundation:${Versions.AndroidX.COMPOSE}"
            const val MATERIAL = "androidx.compose.material:material:${Versions.AndroidX.COMPOSE}"
            const val UI ="androidx.compose.ui:ui:${Versions.AndroidX.COMPOSE_UI}"
            // Tooling support (Previews, etc.)
            const val UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.AndroidX.COMPOSE_UI}"
            const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.AndroidX.COMPOSE_UI}"
        }
    }

    object Jetbrains {
        const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
        object Coroutines {
            const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
            const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
            const val PLAY_SERVICES = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.COROUTINES}"
        }
    }

    object Squareup {
        const val RETROFIT_MOSHI = "com.squareup.retrofit2:converter-moshi:${Versions.Squareup.RETROFIT}"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Squareup.RETROFIT}"
        const val OKHTTP3 = "com.squareup.okhttp3:logging-interceptor:${Versions.Squareup.OKHTTP3}"
    }

    object Others {
        //for image rendering
        const val COIL = "io.coil-kt:coil-compose:${Versions.COIL}"
    }

    object Test {
        object Unit {
            const val JUNIT = "junit:junit:${Versions.Test.JUNIT}"
            const val MOCKK = "io.mockk:mockk:${Versions.Test.MOCKK}"
            const val CORE_TESTING = "android.arch.core:core-testing:${Versions.Test.CORE_TESTING}"
        }

        object Integration {
            const val JUNIT = "androidx.test.ext:junit:${Versions.Test.JUNIT_INTEGRATION}"
            const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.Test.ESPRESSO}"
            const val ESPRESSO_INTENTS = "androidx.test.espresso:espresso-intents:${Versions.Test.ESPRESSO}"
            const val MOCK_WEBSERVER = "com.squareup.okhttp3:mockwebserver:${Versions.Test.MOCK_WEBSERVER}"
            const val MOCKK = "io.mockk:mockk-android:${Versions.Test.MOCKK}"
            const val COMPOSE_UI_TEST = "androidx.compose.ui:ui-test-junit4:${Versions.Test.COMPOSE_UI_TEST}"
            const val RUNNER = "androidx.test:runner:${Versions.Test.RUNNER}"
            const val ORCHESTRATOR = "androidx.test:orchestrator:${Versions.Test.RUNNER}"
        }
    }

    fun DependencyHandlerScope.commonView() {
        "implementation"(Google.MATERIAL)

        "implementation"(AndroidX.ACTIVITY_COMPOSE)
        "implementation"(AndroidX.LIFECYCLE)
        "implementation"(AndroidX.FRAGMENT)
        "implementation"(AndroidX.Compose.FOUNDATION)
        "implementation"(AndroidX.Compose.MATERIAL)
        "implementation"(AndroidX.Compose.UI)
        "implementation"(AndroidX.Compose.UI_TOOLING_PREVIEW)
        "debugImplementation"(AndroidX.Compose.UI_TOOLING)
        "implementation"(Others.COIL)
    }

    fun DependencyHandlerScope.common() {
        "implementation"(AndroidX.APPCOMPAT)
        "implementation"(AndroidX.CORE_KTX)
        "implementation"(Jetbrains.KOTLIN_STDLIB)

        "implementation"(Google.Hilt.ANDROID)
        "kapt"(Google.Hilt.ANDROID_COMPILER)

        "implementation"(Jetbrains.Coroutines.CORE)
        "implementation"(Jetbrains.Coroutines.ANDROID)
        "implementation"(Jetbrains.Coroutines.PLAY_SERVICES)
    }

    fun DependencyHandlerScope.commonUnitTest() {
        "testImplementation"(Test.Unit.JUNIT)
    }

    fun DependencyHandlerScope.commonIntegrationTest() {
        "androidTestImplementation"(Test.Integration.JUNIT)
        "androidTestImplementation"(Test.Integration.ESPRESSO_CORE)
        "androidTestImplementation"(Test.Integration.ESPRESSO_INTENTS)
        "androidTestImplementation"(Test.Integration.MOCK_WEBSERVER)
        "androidTestImplementation"(Test.Integration.MOCKK)
        "androidTestImplementation"(Test.Integration.COMPOSE_UI_TEST)
        "androidTestImplementation"(Test.Integration.RUNNER)
        "androidTestUtil"(Test.Integration.ORCHESTRATOR)
    }
}