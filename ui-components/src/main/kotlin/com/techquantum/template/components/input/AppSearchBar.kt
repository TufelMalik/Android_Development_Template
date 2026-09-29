package com.techquantum.template.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.techquantum.template.ui.theme.AppTheme

@Composable
fun AppSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    onSearch: ((String) -> Unit)? = null,
    onClear: (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.sizing.searchBarHeight),
        placeholder = { Text(text = placeholder, style = AppTheme.typography.bodyMedium) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = AppTheme.colors.onSurfaceVariant,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                    onClear?.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = AppTheme.colors.onSurfaceVariant,
                    )
                }
            }
        },
        singleLine = true,
        shape = AppTheme.shapes.full,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch?.invoke(query) }),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = AppTheme.colors.surfaceVariant.copy(alpha = 0.5f),
            unfocusedContainerColor = AppTheme.colors.surfaceVariant.copy(alpha = 0.5f),
            focusedBorderColor = AppTheme.colors.primary,
            unfocusedBorderColor = AppTheme.colors.outlineVariant,
        ),
    )
}
