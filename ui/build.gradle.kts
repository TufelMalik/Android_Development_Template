plugins {
    id("template.android.library")
    id("template.android.compose")
}

android {
    namespace = "com.techquantum.template.ui"
}

dependencies {
    implementation(project(":core-common"))
    implementation(libs.androidx.core.ktx)
}
