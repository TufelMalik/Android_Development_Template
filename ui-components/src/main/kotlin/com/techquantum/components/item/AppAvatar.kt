package com.techquantum.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = AppTheme.sizing.avatarMd,
    backgroundColor: Color = AppTheme.colors.primaryContainer,
    textColor: Color = AppTheme.colors.onPrimaryContainer,
) {
    val initial = name.trim().firstOrNull()?.uppercaseChar()?.toString() ?: "?"

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initial,
            style = AppTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )
    }
}
