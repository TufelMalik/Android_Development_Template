import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal object KotlinAndroid {
    const val COMPILE_SDK = 37
    const val MIN_SDK = 24

    fun configureKotlinAndroid(
        project: Project,
        libraryExtension: LibraryExtension,
    ) {
        libraryExtension.apply {
            compileSdk = COMPILE_SDK

            defaultConfig {
                minSdk = MIN_SDK
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }
    }
}
