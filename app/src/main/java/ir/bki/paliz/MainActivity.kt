package ir.bki.paliz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import ir.bki.designsystem.theme.PalizTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()

        setContent {
            PalizTheme {
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                splashScreen.setKeepOnScreenCondition { false }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Text(text = BuildConfig.VERSION_NAME)
                        Text(text = BuildConfig.APPLICATION_ID)
                        Text(text = BuildConfig.FLAVOR)
                        Text(BuildConfig.VERSION_CODE.toString())
                        Text(
                            text = BuildConfig.AUTH_BASE_URL,
                            modifier = Modifier.padding(innerPadding),
                        )
                        Text(
                            text = BuildConfig.SETTING_BASE_URL,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}
