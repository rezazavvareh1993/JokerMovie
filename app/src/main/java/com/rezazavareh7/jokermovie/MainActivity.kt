package com.rezazavareh7.jokermovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.rezazavareh7.designsystem.component.navigation.SystemBarManager
import com.rezazavareh7.designsystem.theme.JokerMovieTheme
import com.rezazavareh7.jokermovie.navgraph.NavigationBar
import com.rezazavareh7.jokermovie.navgraph.RootNavGraph
import com.rezazavareh7.jokermovie.util.SetStatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val systemBarManager = SystemBarManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        setContent {
            JokerMovieTheme {
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                splashScreen.setKeepOnScreenCondition { false }

                SetStatusBarColor(systemBarManager)

                val navController = rememberNavController()
                var isNavigateFromOut by remember {
                    mutableStateOf(false)
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        Scaffold(
                            bottomBar = {
                                if (systemBarManager.isBottomBarVisible.value) {
                                    NavigationBar(
                                        navController,
                                        selectedItemIndex = selectedItemIndex,
                                        isNavigateFromOut = isNavigateFromOut,
                                        onItemSelected = { index ->
                                            selectedItemIndex = index
                                            isNavigateFromOut = false
                                        },
                                    )
                                }
                            },
                        ) { innerPadding ->
                            Box(modifier = Modifier.padding(innerPadding)) {
                                val isUserLoggedIn = true
                                if (!isUserLoggedIn) {
                                    selectedItemIndex = 0
                                }
                                RootNavGraph(
                                    navController = navController,
                                    systemBarManager = systemBarManager,
                                    isUserLoggedIn = isUserLoggedIn,
                                    navigateToPasswordVerification = { },
                                    isNavigateToSpecialRouteOfBottomNavigation = { navigateToSpecialIndexOfBottomNavigation ->
                                        systemBarManager.showBottomBar()
                                        isNavigateFromOut = true
                                        selectedItemIndex = navigateToSpecialIndexOfBottomNavigation
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
