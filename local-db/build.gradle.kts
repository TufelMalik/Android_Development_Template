plugins {
    id("template.android.library")
    id("template.koin")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.techquantum.template.localdb"
}

ksp {
    arg("room.generateKotlin", "true")
}

dependencies {
    implementation(project(":core-common"))
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
}
