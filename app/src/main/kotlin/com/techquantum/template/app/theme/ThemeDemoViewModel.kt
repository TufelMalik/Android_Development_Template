package com.techquantum.template.app.theme

import androidx.lifecycle.ViewModel
import com.techquantum.ui.core.UiCore
import com.techquantum.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeDemoViewModel : ViewModel() {
    private val _themeMode = MutableStateFlow(UiCore.defaultThemeMode)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }
}
