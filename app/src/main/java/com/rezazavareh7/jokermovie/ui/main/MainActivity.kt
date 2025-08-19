package com.rezazavareh7.jokermovie.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.rezazavareh7.designsystem.component.navigation.SystemBarManager
import com.rezazavareh7.designsystem.theme.JokerMovieTheme
import com.rezazavareh7.jokermovie.navgraph.RootNavGraph
import com.rezazavareh7.jokermovie.util.LocaleManager
import com.rezazavareh7.jokermovie.util.SetStatusBarColor
import com.rezazavareh7.movies.ui.setting.ThemeSegmentButtonType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var localeManager: LocaleManager
    private val systemBarManager = SystemBarManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val mainUiState by viewModel.mainState.collectAsStateWithLifecycle()
            val context = this
            LaunchedEffect(mainUiState.currentLanguage) {
                if (::localeManager.isInitialized && mainUiState.currentLanguage != null) {
                    triggerRecreation()
                }
            }
            JokerMovieTheme(darkTheme = mainUiState.currentTheme == ThemeSegmentButtonType.DARK) {
                splashScreen.setKeepOnScreenCondition { false }

                SetStatusBarColor(systemBarManager)

                val navController = rememberNavController()

                Box(modifier = Modifier.Companion.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.Companion.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        Scaffold { innerPadding ->
                            Box(
                                modifier =
                                    Modifier.Companion
                                        .padding(innerPadding)
                                        .consumeWindowInsets(innerPadding),
                            ) {
                                RootNavGraph(
                                    navController = navController,
                                    systemBarManager = systemBarManager,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun triggerRecreation() {
        recreate()
    }

    override fun onResume() {
        if (::localeManager.isInitialized) {
            localeManager.setLocale(this)
        }
        super.onResume()
    }
}
