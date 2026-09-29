package com.techquantum.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.techquantum.components.core.ComponentsCore
import com.techquantum.components.state.DefaultEmptyState
import com.techquantum.components.state.DefaultErrorState
import com.techquantum.components.state.DefaultLoadingState
import com.techquantum.ui.theme.AppTheme

/**
 * AppLazyGrid is the core responsive list/grid component.
 * On compact screens (phones), it lays out items in a single vertical column.
 * On medium and expanded screens (tablets/foldables), it automatically scales to multi-column.
 */
@Composable
fun <T> AppLazyGrid(
    state: ListUiState<T>,
    modifier: Modifier = Modifier,
    key: ((T) -> Any)? = null,
    columnsOverride: Int? = null,
    gridState: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(AppTheme.spacing.md),
    verticalSpacing: Dp = ComponentsCore.gridItemSpacing,
    horizontalSpacing: Dp = ComponentsCore.gridItemSpacing,
    loadingContent: @Composable () -> Unit = { DefaultLoadingState() },
    emptyContent: @Composable (message: String?) -> Unit = { DefaultEmptyState(message = it) },
    errorContent: @Composable (message: String, onRetry: (() -> Unit)?) -> Unit = { msg, retry ->
        DefaultErrorState(message = msg, onRetry = retry)
    },
    headerContent: (LazyGridScope.() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit,
) {
    val windowSizeClass = AppTheme.windowSizeClass
    val columnCount = columnsOverride ?: ComponentsCore.resolveColumnCount(windowSizeClass)

    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is ListUiState.Loading -> {
                loadingContent()
            }
            is ListUiState.Empty -> {
                emptyContent(state.message)
            }
            is ListUiState.Error -> {
                errorContent(state.message, state.onRetry)
            }
            is ListUiState.Content -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnCount),
                    state = gridState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = contentPadding,
                    verticalArrangement = Arrangement.spacedBy(verticalSpacing),
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
                ) {
                    headerContent?.invoke(this)
                    items(
                        items = state.items,
                        key = key,
                    ) { item ->
                        itemContent(item)
                    }
                }
            }
        }
    }
}
