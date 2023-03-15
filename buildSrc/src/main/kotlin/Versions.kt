import org.gradle.api.JavaVersion

object Versions {
    object App {
        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0.0"
        const val MIN_SDK = 23
        const val TARGET_SDK = 33
        const val COMPILE_SDK = 33
    }

    object Google {
        const val HILT = "2.45"
        const val MATERIAL = "1.8.0"
    }

    object AndroidX {
        const val CORE_KTX = "1.9.0"
        const val APPCOMPAT = "1.6.1"
        const val LIFECYCLE = "2.6.0"
        const val ACTIVITY_COMPOSE = "1.6.1"
        const val COMPOSE = "1.3.1"
        const val COMPOSE_UI = "1.3.3"
        const val COMPOSE_COMPILER = "1.4.3"
    }

    object Squareup {
        const val RETROFIT = "2.9.0"
        const val OKHTTP3 = "4.10.0"
    }

    object Test {
        const val JUNIT = "4.13.2"
        const val CORE_TESTING = "1.1.1"
        const val JUNIT_INTEGRATION = "1.1.2"
        const val ESPRESSO = "3.4.0"
        const val COMPOSE_UI_TEST = "1.2.1"

        const val MOCKK = "1.8.12.kotlin13"
        const val MOCK_WEBSERVER = "3.12.0"
        const val RUNNER = "1.1.0"
    }

    const val COROUTINES = "1.6.4"

    const val COIL = "2.2.2"

    // Make sure to update `buildSrc/build.gradle.kts` when updating this
    const val GRADLE = "7.4.1"
    // Make sure to update `buildSrc/build.gradle.kts` when updating this
    const val KOTLIN = "1.8.10"

    val JAVA = JavaVersion.VERSION_1_8
}