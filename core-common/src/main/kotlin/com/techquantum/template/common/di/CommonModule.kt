package com.techquantum.template.common.di

import com.techquantum.template.common.dispatcher.DefaultDispatcherProvider
import com.techquantum.template.common.dispatcher.DispatcherProvider
import com.techquantum.template.common.logger.AppLogger
import com.techquantum.template.common.logger.DefaultAppLogger
import org.koin.dsl.module

val commonModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
    single<AppLogger> { DefaultAppLogger() }
}
