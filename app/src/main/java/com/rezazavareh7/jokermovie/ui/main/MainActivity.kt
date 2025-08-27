package com.rezazavareh7.jokermovie.ui.main

import android.app.Activity
import android.content.Intent
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
                if (mainUiState.currentLanguage.isNotEmpty() && ::localeManager.isInitialized &&
                    mainUiState.currentLanguage.split("-")
                        .first() != context.resources.configuration.locales[0].language
                ) {
                    restartApp(context)
                }
            }
            JokerMovieTheme(darkTheme = mainUiState.currentTheme == ThemeSegmentButtonType.DARK) {
                splashScreen.setKeepOnScreenCondition { false }

                SetStatusBarColor(systemBarManager)

                val navController = rememberNavController()

                Box(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        Scaffold { innerPadding ->
                            Box(
                                modifier =
                                    Modifier
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

    fun restartApp(activity: Activity) {
        if (::localeManager.isInitialized) {
            localeManager.setLocale(this) {
                val intent = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivity(intent)
                activity.finishAffinity()
            }
        }
    }
}
