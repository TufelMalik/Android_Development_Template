package com.techquantum.template.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.techquantum.template.ui.theme.AppTheme

/**
 * AppLazyRow is a thin horizontal scrollable container for one-dimensional content
 * such as chip rows, tag lists, or horizontal media carousels.
 */
@Composable
fun <T> AppLazyRow(
    items: List<T>,
    modifier: Modifier = Modifier,
    key: ((T) -> Any)? = null,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(horizontal = AppTheme.spacing.md),
    horizontalSpacing: Dp = AppTheme.spacing.sm,
    itemContent: @Composable (T) -> Unit,
) {
    LazyRow(
        state = listState,
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
    ) {
        items(
            items = items,
            key = key,
        ) { item ->
            itemContent(item)
        }
    }
}
