plugins {
    id(Plugins.APPLICATION)
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.bbrustol.spacex"

    hilt {
        enableExperimentalClasspathAggregation = false
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(project(Modules.UIKIT))
    implementation(project(Modules.FEATURES))
}