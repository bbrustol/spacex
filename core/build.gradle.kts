import Dependencies.Squareup

plugins {
    id(Plugins.COMMON_LIBRARY)
}
android {
    namespace = "com.bbrustol.core"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://api.spacexdata.com/v3/\"")
    }

    dependencies {
        implementation (Squareup.RETROFIT)
        implementation (Squareup.OKHTTP3)

        testImplementation(kotlin("test"))
    }
}
