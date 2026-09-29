package com.techquantum.template.app.di

import com.techquantum.template.app.grid.GridDemoViewModel
import com.techquantum.template.app.localdb.RoomDemoViewModel
import com.techquantum.template.app.network.FirebaseDemoViewModel
import com.techquantum.template.app.network.KtorDemoViewModel
import com.techquantum.template.app.theme.ThemeDemoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        ThemeDemoViewModel()
    }

    viewModel {
        GridDemoViewModel()
    }

    viewModel {
        KtorDemoViewModel(
            apiService = get(),
        )
    }

    viewModel {
        FirebaseDemoViewModel(
            firestoreDataSource = get(),
            realtimeDbDataSource = get(),
        )
    }

    viewModel {
        RoomDemoViewModel(
            sampleDao = get(),
            localDataSource = get(),
        )
    }
}
