import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.findByType(LibraryExtension::class.java)?.apply {
                buildFeatures {
                    compose = true
                }
            }

            dependencies.apply {
                val bom = libs.findLibrary("androidx.compose.bom").get()
                add("implementation", platform(bom))
                add("androidTestImplementation", platform(bom))
                add("implementation", libs.findLibrary("androidx.compose.ui").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.graphics").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx.compose.material3").get())
                add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
                add("debugImplementation", libs.findLibrary("androidx.compose.ui.test.manifest").get())
            }
        }
    }
}
