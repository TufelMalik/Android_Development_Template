package com.techquantum.template.app.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techquantum.template.common.result.AppResult
import com.techquantum.components.list.ListUiState
import com.techquantum.template.localdb.base.LocalDataSource
import com.techquantum.template.localdb.database.dao.SampleDao
import com.techquantum.template.localdb.database.entity.SampleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class RoomDemoViewModel(
    private val sampleDao: SampleDao,
    private val localDataSource: LocalDataSource<SampleEntity>,
) : ViewModel() {

    val itemsState: StateFlow<ListUiState<SampleEntity>> = sampleDao.observeAll()
        .map { items ->
            if (items.isEmpty()) {
                ListUiState.Empty("No entities in Room database. Insert one above!")
            } else {
                ListUiState.Content(items)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListUiState.Loading,
        )

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    fun insertSample(title: String, description: String?) {
        if (title.isBlank()) return
        viewModelScope.launch {
            val entity = SampleEntity(
                id = UUID.randomUUID().toString(),
                title = title.trim(),
                description = description?.trim(),
            )
            when (val result = localDataSource.insert(entity)) {
                is AppResult.Success -> {
                    _statusMessage.value = "Inserted entity '${entity.title}' (Row ID: ${result.data})"
                }
                is AppResult.Failure -> {
                    _statusMessage.value = "Insert failed: ${result.error.message}"
                }
            }
        }
    }

    fun deleteSample(entity: SampleEntity) {
        viewModelScope.launch {
            when (val result = localDataSource.delete(entity)) {
                is AppResult.Success -> {
                    _statusMessage.value = "Deleted entity '${entity.title}'"
                }
                is AppResult.Failure -> {
                    _statusMessage.value = "Delete failed: ${result.error.message}"
                }
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            sampleDao.clearAll()
            _statusMessage.value = "Cleared all entities from Room database"
        }
    }
}
