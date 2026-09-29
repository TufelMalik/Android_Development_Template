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
import com.techquantum.network.ktor.core.KtorCore
import com.techquantum.ui.theme.AppTheme

@Composable
fun KtorDemoScreen(
    viewModel: KtorDemoViewModel,
    modifier: Modifier = Modifier,
) {
    val postsState by viewModel.postsState.collectAsState()
    val isSubmitting by viewModel.isSubmitting.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()

    var postTitle by remember { mutableStateOf("") }
    var postBody by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.md),
    ) {
        // Endpoint info card
        AppCard(variant = CardVariant.Outlined) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Ktor HTTP Client Demo",
                    style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.titleMedium,
                    color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xxs))
                Text(
                    text = "Base URL: ${_root_ide_package_.com.techquantum.network.ktor.core.KtorCore.defaultBaseUrl}",
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

        // Create Post Form (POST test)
        AppCard(variant = CardVariant.Elevated) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Create Post (POST non-idempotent)",
                    style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.titleSmall,
                    color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xs))
                AppTextField(
                    value = postTitle,
                    onValueChange = { postTitle = it },
                    placeholder = "Post title...",
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xs))
                AppTextField(
                    value = postBody,
                    onValueChange = { postBody = it },
                    placeholder = "Post content...",
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.sm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(com.techquantum.ui.theme.AppTheme.spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppButton(
                        text = "Sample Data",
                        onClick = { viewModel.loadSampleData() },
                        variant = ButtonVariant.Outline,
                        size = ButtonSize.Small,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AppButton(
                        text = "Fetch GET",
                        onClick = { viewModel.fetchPosts() },
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Small,
                    )
                    AppButton(
                        text = "Submit POST",
                        onClick = {
                            viewModel.createPost(postTitle, postBody)
                            postTitle = ""
                            postBody = ""
                        },
                        loading = isSubmitting,
                        enabled = postTitle.isNotBlank(),
                        size = ButtonSize.Small,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.md))

        // Posts List
        AppLazyGrid(
            state = postsState,
            key = { it.id },
            modifier = Modifier.weight(1f),
        ) { post ->
            AppCard(variant = CardVariant.Filled) {
                AppListItem(
                    headline = "#${post.id} ${post.title}",
                    supportingText = post.body,
                    leadingContent = {
                        AppAvatar(name = post.title, size = _root_ide_package_.com.techquantum.ui.theme.AppTheme.sizing.avatarSm)
                    },
                )
            }
        }
    }
}
