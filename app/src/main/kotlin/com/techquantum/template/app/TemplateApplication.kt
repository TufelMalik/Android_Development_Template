package com.techquantum.template.app

import android.app.Application
import com.techquantum.template.app.di.appModule
import com.techquantum.template.common.di.commonModule
import com.techquantum.template.localdb.di.databaseModule
import com.techquantum.template.network.firebase.di.firebaseModule
import com.techquantum.template.network.ktor.di.ktorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TemplateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TemplateApplication)
            modules(
                commonModule,
                ktorModule,
                firebaseModule,
                databaseModule,
                appModule,
            )
        }
    }
}
