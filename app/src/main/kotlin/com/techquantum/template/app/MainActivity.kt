package com.techquantum.template.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Http
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.techquantum.template.app.grid.GridDemoScreen
import com.techquantum.template.app.grid.GridDemoViewModel
import com.techquantum.template.app.localdb.RoomDemoScreen
import com.techquantum.template.app.localdb.RoomDemoViewModel
import com.techquantum.template.app.network.FirebaseDemoScreen
import com.techquantum.template.app.network.FirebaseDemoViewModel
import com.techquantum.template.app.network.KtorDemoScreen
import com.techquantum.template.app.network.KtorDemoViewModel
import com.techquantum.template.app.theme.ThemeDemoScreen
import com.techquantum.template.app.theme.ThemeDemoViewModel
import com.techquantum.template.components.feedback.snackbar.AppSnackbarHost
import com.techquantum.template.components.feedback.toast.AppToastHost
import com.techquantum.template.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

enum class DemoTab(val title: String, val icon: ImageVector) {
    THEME("Theme", Icons.Default.ColorLens),
    GRID("Grid & UI", Icons.Default.GridOn),
    ROOM("Room DB", Icons.Default.Storage),
    KTOR("Ktor API", Icons.Default.Http),
    FIREBASE("Firebase", Icons.Default.Cloud),
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeViewModel: ThemeDemoViewModel = koinViewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()

            AppTheme(themeMode = themeMode) {
                MainAppScreen(themeViewModel = themeViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    themeViewModel: ThemeDemoViewModel,
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = DemoTab.entries
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Android Template: ${tabs[selectedTab].title}",
                        style = AppTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colors.surface,
                    titleContentColor = AppTheme.colors.onSurface,
                ),
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = AppTheme.colors.surface,
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(imageVector = tab.icon, contentDescription = tab.title) },
                        label = { Text(text = tab.title) },
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (tabs[selectedTab]) {
                DemoTab.THEME -> ThemeDemoScreen(viewModel = themeViewModel)
                DemoTab.GRID -> {
                    val gridViewModel: GridDemoViewModel = koinViewModel()
                    GridDemoScreen(viewModel = gridViewModel)
                }
                DemoTab.ROOM -> {
                    val roomViewModel: RoomDemoViewModel = koinViewModel()
                    RoomDemoScreen(viewModel = roomViewModel)
                }
                DemoTab.KTOR -> {
                    val ktorViewModel: KtorDemoViewModel = koinViewModel()
                    KtorDemoScreen(viewModel = ktorViewModel)
                }
                DemoTab.FIREBASE -> {
                    val firebaseViewModel: FirebaseDemoViewModel = koinViewModel()
                    FirebaseDemoScreen(viewModel = firebaseViewModel)
                }
            }

            // Global decoupled feedback hosts
            AppSnackbarHost()
            AppToastHost()
        }
    }
}
