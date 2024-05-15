package com.test

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.core.ui.theme.MagicDownloaderTheme
import com.splash.navigation.splashScreenRoute
import com.test.nav_graph.appNavGraph
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        val navController = rememberNavController()
        val hazeState = koinInject<HazeState>()

        MagicDownloaderTheme {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .haze(state = hazeState)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.weight(weight = 1f)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = splashScreenRoute,
                        builder = { appNavGraph(navController = navController) },
                    )
                }
            }
        }
    }
}