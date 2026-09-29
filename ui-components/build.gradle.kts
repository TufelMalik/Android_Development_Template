plugins {
    id("template.android.library")
    id("template.android.compose")
    id("template.koin")
}

android {
    namespace = "com.techquantum.template.components"
}

dependencies {
    implementation(project(":ui"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material.icons.extended)
}
