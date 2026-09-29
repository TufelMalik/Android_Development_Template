plugins {
    id("template.android.library")
    id("template.koin")
}

android {
    namespace = "com.techquantum.template.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}
