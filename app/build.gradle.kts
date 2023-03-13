plugins {
    id(Plugins.APPLICATION)
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.bbrustol.newarchtest"

    hilt {
        enableExperimentalClasspathAggregation = false
        enableAggregatingTask = true
    }
}

dependencies {
}