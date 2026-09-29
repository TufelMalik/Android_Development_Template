package com.techquantum.template.app.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techquantum.template.common.result.AppResult
import com.techquantum.template.components.list.ListUiState
import com.techquantum.template.network.ktor.api.ApiService
import com.techquantum.template.network.ktor.api.get
import com.techquantum.template.network.ktor.api.post
import com.techquantum.template.network.ktor.request.ApiRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Int = 0,
    val userId: Int = 1,
    val title: String,
    val body: String,
)

class KtorDemoViewModel(
    private val apiService: ApiService,
) : ViewModel() {

    private val _postsState = MutableStateFlow<ListUiState<PostDto>>(ListUiState.Empty("Tap 'Fetch Posts' to test Ktor GET"))
    val postsState: StateFlow<ListUiState<PostDto>> = _postsState.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    fun fetchPosts() {
        viewModelScope.launch {
            _postsState.value = ListUiState.Loading
            val request = ApiRequest(path = "posts")
            when (val result: AppResult<List<PostDto>> = apiService.get(request)) {
                is AppResult.Success -> {
                    _postsState.value = ListUiState.Content(result.data.take(10))
                    _statusMessage.value = "Successfully fetched ${result.data.size} posts (showing 10)"
                }
                is AppResult.Failure -> {
                    _postsState.value = ListUiState.Error(
                        message = result.error.message,
                        onRetry = { fetchPosts() },
                    )
                    _statusMessage.value = "GET Error: ${result.error.message}"
                }
            }
        }
    }

    fun createPost(title: String, body: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            _isSubmitting.value = true
            val request = ApiRequest(path = "posts")
            val newPost = PostDto(title = title, body = body)

            when (val result: AppResult<PostDto> = apiService.post(request, newPost)) {
                is AppResult.Success -> {
                    _statusMessage.value = "POST Success: Created post #${result.data.id} ('${result.data.title}')"
                }
                is AppResult.Failure -> {
                    _statusMessage.value = "POST Error: ${result.error.message}"
                }
            }
            _isSubmitting.value = false
        }
    }
}
