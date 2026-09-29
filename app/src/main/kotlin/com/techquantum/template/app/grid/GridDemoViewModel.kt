package com.techquantum.template.app.grid

import androidx.lifecycle.ViewModel
import com.techquantum.template.components.list.ListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DemoItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
)

class GridDemoViewModel : ViewModel() {
    private val sampleItems = listOf(
        DemoItem("1", "Architecture Blueprint", "Clean Multi-Module Setup", "Core"),
        DemoItem("2", "Ktor HTTP Client", "Lenient Serialization & Retry", "Network"),
        DemoItem("3", "Firebase Integrations", "Firestore, Realtime DB, Auth", "Network"),
        DemoItem("4", "Room Local Database", "Safe Transactions & BaseDao", "Database"),
        DemoItem("5", "Adaptive Grid Columns", "Responsive Window Classifier", "UI"),
        DemoItem("6", "Design Tokens", "UiCore Global Theming", "UI"),
    )

    private val _uiState = MutableStateFlow<ListUiState<DemoItem>>(ListUiState.Content(sampleItems))
    val uiState: StateFlow<ListUiState<DemoItem>> = _uiState.asStateFlow()

    fun showContent() {
        _uiState.value = ListUiState.Content(sampleItems)
    }

    fun showLoading() {
        _uiState.value = ListUiState.Loading
    }

    fun showEmpty() {
        _uiState.value = ListUiState.Empty("No items found. Try switching states!")
    }

    fun showError() {
        _uiState.value = ListUiState.Error(
            message = "Simulated connection error. Tap retry to reload content.",
            onRetry = { showContent() },
        )
    }
}
