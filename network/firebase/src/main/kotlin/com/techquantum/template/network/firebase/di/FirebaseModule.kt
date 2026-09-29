package com.techquantum.template.network.firebase.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.storage.FirebaseStorage
import com.techquantum.template.network.firebase.auth.AuthDataSource
import com.techquantum.template.network.firebase.auth.AuthDataSourceImpl
import com.techquantum.template.network.firebase.core.FirebaseCore
import com.techquantum.template.network.firebase.firestore.FirestoreDataSource
import com.techquantum.template.network.firebase.firestore.FirestoreDataSourceImpl
import com.techquantum.template.network.firebase.realtimedb.RealtimeDbDataSource
import com.techquantum.template.network.firebase.realtimedb.RealtimeDbDataSourceImpl
import com.techquantum.template.network.firebase.storage.StorageDataSource
import com.techquantum.template.network.firebase.storage.StorageDataSourceImpl
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseFirestore> {
        val firestore = FirebaseFirestore.getInstance()
        val settingsBuilder = FirebaseFirestoreSettings.Builder()
        if (FirebaseCore.isOfflinePersistenceEnabled) {
            settingsBuilder.setLocalCacheSettings(PersistentCacheSettings.newBuilder().build())
        }
        firestore.firestoreSettings = settingsBuilder.build()
        firestore
    }

    single<FirebaseDatabase> {
        val db = try {
            FirebaseDatabase.getInstance(FirebaseCore.realtimeDbUrl)
        } catch (_: Exception) {
            FirebaseDatabase.getInstance()
        }
        try {
            db.setPersistenceEnabled(FirebaseCore.isOfflinePersistenceEnabled)
        } catch (_: Exception) {
            // Persistence can only be set before any other usage
        }
        db
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<FirebaseStorage> {
        try {
            FirebaseStorage.getInstance(FirebaseCore.storageBucket)
        } catch (_: Exception) {
            FirebaseStorage.getInstance()
        }
    }

    single<FirestoreDataSource> {
        FirestoreDataSourceImpl(
            firestore = get(),
            dispatcherProvider = get(),
            appLogger = get(),
        )
    }

    single<RealtimeDbDataSource> {
        RealtimeDbDataSourceImpl(
            database = get(),
            dispatcherProvider = get(),
            appLogger = get(),
        )
    }

    single<AuthDataSource> {
        AuthDataSourceImpl(
            auth = get(),
            dispatcherProvider = get(),
        )
    }

    single<StorageDataSource> {
        StorageDataSourceImpl(
            storage = get(),
            dispatcherProvider = get(),
        )
    }
}
