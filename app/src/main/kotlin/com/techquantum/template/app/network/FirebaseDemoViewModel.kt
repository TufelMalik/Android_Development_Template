package com.techquantum.template.app.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techquantum.template.common.result.AppResult
import com.techquantum.components.list.ListUiState
import com.techquantum.template.network.firebase.firestore.FirestoreDataSource
import com.techquantum.template.network.firebase.firestore.QuerySpec
import com.techquantum.template.network.firebase.firestore.getDocument
import com.techquantum.template.network.firebase.firestore.getList
import com.techquantum.template.network.firebase.realtimedb.RealtimeDbDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FirebaseItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
)

class FirebaseDemoViewModel(
    private val firestoreDataSource: FirestoreDataSource,
    private val realtimeDbDataSource: RealtimeDbDataSource,
) : ViewModel() {

    private val _itemsState = MutableStateFlow<ListUiState<FirebaseItem>>(
        ListUiState.Empty("Connect your google-services.json and tap 'Query Firestore' to test"),
    )
    val itemsState: StateFlow<ListUiState<FirebaseItem>> = _itemsState.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    fun queryFirestore() {
        viewModelScope.launch {
            _itemsState.value = ListUiState.Loading
            val querySpec = QuerySpec(
                collectionPath = "demo_items",
                limit = 10,
            )
            when (val result = firestoreDataSource.getList<FirebaseItem>(querySpec)) {
                is AppResult.Success -> {
                    if (result.data.isEmpty()) {
                        _itemsState.value = ListUiState.Empty("No items found in 'demo_items' collection")
                    } else {
                        _itemsState.value = ListUiState.Content(result.data)
                    }
                    _statusMessage.value = "Fetched ${result.data.size} documents from Firestore"
                }
                is AppResult.Failure -> {
                    _itemsState.value = ListUiState.Error(
                        message = result.error.message,
                        onRetry = { queryFirestore() },
                    )
                    _statusMessage.value = "Firestore Error: ${result.error.message}"
                }
            }
        }
    }

    fun addSampleDocument(name: String, desc: String) {
        viewModelScope.launch {
            val id = "doc_${System.currentTimeMillis()}"
            val item = FirebaseItem(id = id, name = name, description = desc)
            when (val result = firestoreDataSource.setDocument("demo_items/$id", item)) {
                is AppResult.Success -> {
                    _statusMessage.value = "Document created with ID: $id"
                    queryFirestore()
                }
                is AppResult.Failure -> {
                    _statusMessage.value = "Write Error: ${result.error.message}"
                }
            }
        }
    }
}
