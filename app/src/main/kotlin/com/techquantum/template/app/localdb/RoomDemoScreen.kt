package com.techquantum.template.app.localdb

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techquantum.template.components.button.AppButton
import com.techquantum.template.components.button.ButtonSize
import com.techquantum.template.components.button.ButtonVariant
import com.techquantum.template.components.card.AppCard
import com.techquantum.template.components.card.CardVariant
import com.techquantum.template.components.feedback.dialog.ConfirmDialog
import com.techquantum.template.components.input.AppTextField
import com.techquantum.template.components.item.AppAvatar
import com.techquantum.template.components.item.AppListItem
import com.techquantum.template.components.list.AppLazyGrid
import com.techquantum.template.localdb.core.DatabaseCore
import com.techquantum.template.ui.theme.AppTheme

@Composable
fun RoomDemoScreen(
    viewModel: RoomDemoViewModel,
    modifier: Modifier = Modifier,
) {
    val itemsState by viewModel.itemsState.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showClearConfirm by remember { mutableStateOf(false) }

    if (showClearConfirm) {
        ConfirmDialog(
            title = "Clear Database",
            message = "Are you sure you want to delete all entities from the Room database?",
            confirmText = "Delete All",
            isDestructive = true,
            onConfirm = { viewModel.clearAll() },
            onDismissRequest = { showClearConfirm = false },
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.md),
    ) {
        // Database info banner
        AppCard(variant = CardVariant.Outlined) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Room Persistence Demo",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.xxs))
                Text(
                    text = "DB: ${DatabaseCore.databaseName} (v${DatabaseCore.databaseVersion}) | WAL: ${DatabaseCore.enableWal}",
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant,
                )
                if (statusMessage != null) {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
                    Text(
                        text = statusMessage ?: "",
                        style = AppTheme.typography.bodySmall,
                        color = AppTheme.colors.primary,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.sm))

        // Insert entity form
        AppCard(variant = CardVariant.Elevated) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Insert SampleEntity",
                    style = AppTheme.typography.titleSmall,
                    color = AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
                AppTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = "Entity title...",
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
                AppTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "Entity description...",
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.sm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppButton(
                        text = "Clear All",
                        onClick = { showClearConfirm = true },
                        variant = ButtonVariant.Danger,
                        size = ButtonSize.Small,
                    )
                    Spacer(modifier = Modifier.width(AppTheme.spacing.sm))
                    AppButton(
                        text = "Insert",
                        onClick = {
                            viewModel.insertSample(title, description)
                            title = ""
                            description = ""
                        },
                        enabled = title.isNotBlank(),
                        size = ButtonSize.Small,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        // Entities Grid
        AppLazyGrid(
            state = itemsState,
            key = { it.id },
            modifier = Modifier.weight(1f),
        ) { entity ->
            AppCard(variant = CardVariant.Filled) {
                AppListItem(
                    headline = entity.title,
                    supportingText = entity.description ?: "ID: ${entity.id.take(8)}...",
                    leadingContent = {
                        AppAvatar(name = entity.title, size = AppTheme.sizing.avatarSm)
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { viewModel.deleteSample(entity) },
                            modifier = Modifier.size(36.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = AppTheme.colors.error,
                            )
                        }
                    },
                )
            }
        }
    }
}
