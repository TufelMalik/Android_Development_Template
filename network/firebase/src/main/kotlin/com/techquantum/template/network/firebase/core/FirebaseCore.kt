package com.techquantum.template.network.firebase.core

/**
 * FirebaseCore is the single Core file for network:firebase.
 * Holds project-level Firebase endpoints, page limits, offline persistence toggles,
 * and bucket addresses.
 */
object FirebaseCore {
    var realtimeDbUrl: String = "https://template-default.firebaseio.com"
    var storageBucket: String = "gs://template-default.appspot.com"
    const val defaultPageSize: Long = 20L
    const val isOfflinePersistenceEnabled: Boolean = true
}
