package com.techquantum.template.components.list

sealed interface ListUiState<out T> {
    data object Loading : ListUiState<Nothing>
    data class Empty(val message: String? = null) : ListUiState<Nothing>
    data class Error(val message: String, val onRetry: (() -> Unit)? = null) : ListUiState<Nothing>
    data class Content<T>(val items: List<T>) : ListUiState<T>
}
