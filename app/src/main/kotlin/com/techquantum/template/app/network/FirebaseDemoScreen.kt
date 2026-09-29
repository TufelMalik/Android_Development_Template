package com.techquantum.template.app.network

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.techquantum.components.button.AppButton
import com.techquantum.components.button.ButtonSize
import com.techquantum.components.button.ButtonVariant
import com.techquantum.components.card.AppCard
import com.techquantum.components.card.CardVariant
import com.techquantum.components.input.AppTextField
import com.techquantum.components.item.AppAvatar
import com.techquantum.components.item.AppListItem
import com.techquantum.components.list.AppLazyGrid
import com.techquantum.template.network.firebase.core.FirebaseCore
import com.techquantum.ui.theme.AppTheme

@Composable
fun FirebaseDemoScreen(
    viewModel: FirebaseDemoViewModel,
    modifier: Modifier = Modifier,
) {
    val itemsState by viewModel.itemsState.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()

    var itemName by remember { mutableStateOf("") }
    var itemDesc by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.md),
    ) {
        // Core configuration info
        AppCard(variant = CardVariant.Outlined) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Firebase Module Demo",
                    style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.titleMedium,
                    color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xxs))
                Text(
                    text = "RTDB: ${FirebaseCore.realtimeDbUrl}\nStorage: ${FirebaseCore.storageBucket}\nOffline Persistence: ${FirebaseCore.isOfflinePersistenceEnabled}",
                    style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.bodySmall,
                    color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.onSurfaceVariant,
                )
                if (statusMessage != null) {
                    Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xs))
                    Text(
                        text = statusMessage ?: "",
                        style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.bodySmall,
                        color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.primary,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.sm))

        // Create document form
        AppCard(variant = CardVariant.Elevated) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Add Document to 'demo_items'",
                    style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.titleSmall,
                    color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xs))
                AppTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    placeholder = "Item name...",
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xs))
                AppTextField(
                    value = itemDesc,
                    onValueChange = { itemDesc = it },
                    placeholder = "Description...",
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.sm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppButton(
                        text = "Query Firestore",
                        onClick = { viewModel.queryFirestore() },
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Small,
                    )
                    Spacer(modifier = Modifier.width(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.sm))
                    AppButton(
                        text = "Add Document",
                        onClick = {
                            viewModel.addSampleDocument(itemName, itemDesc)
                            itemName = ""
                            itemDesc = ""
                        },
                        enabled = itemName.isNotBlank(),
                        size = ButtonSize.Small,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.md))

        // Document list
        AppLazyGrid(
            state = itemsState,
            key = { it.id },
            modifier = Modifier.weight(1f),
        ) { item ->
            AppCard(variant = CardVariant.Filled) {
                AppListItem(
                    headline = item.name,
                    supportingText = item.description,
                    leadingContent = {
                        AppAvatar(name = item.name, size = _root_ide_package_.com.techquantum.ui.theme.AppTheme.sizing.avatarSm)
                    },
                )
            }
        }
    }
}
