plugins {
    id("template.android.library")
    id("template.koin")
}

android {
    namespace = "com.techquantum.template.network.firebase"
}

dependencies {
    implementation(project(":core-common"))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)
    implementation(libs.kotlinx.coroutines.core)
}
