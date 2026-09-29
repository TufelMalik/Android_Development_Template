package com.techquantum.template.localdb.di

import com.techquantum.template.localdb.base.LocalDataSource
import com.techquantum.template.localdb.base.LocalDataSourceImpl
import com.techquantum.template.localdb.database.AppDatabase
import com.techquantum.template.localdb.database.DatabaseFactory
import com.techquantum.template.localdb.database.dao.SampleDao
import com.techquantum.template.localdb.database.entity.SampleEntity
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        DatabaseFactory.create(context = androidContext())
    }

    single<SampleDao> {
        get<AppDatabase>().sampleDao()
    }

    single<LocalDataSource<SampleEntity>> {
        LocalDataSourceImpl(
            dao = get<SampleDao>(),
            database = get<AppDatabase>(),
            dispatcherProvider = get(),
        )
    }
}
