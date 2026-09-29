package com.techquantum.template.app

import android.app.Application
import com.techquantum.template.app.di.appModule
import com.techquantum.template.common.di.commonModule
import com.techquantum.template.localdb.di.databaseModule
import com.techquantum.template.network.firebase.di.firebaseModule
import com.techquantum.network.ktor.di.ktorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TemplateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TemplateApplication)
            modules(
                commonModule,
                _root_ide_package_.com.techquantum.network.ktor.di.ktorModule,
                firebaseModule,
                databaseModule,
                appModule,
            )
        }
    }
}
