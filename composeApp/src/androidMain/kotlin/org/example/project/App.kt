package org.example.project

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.destinations.AboutDestination
import org.example.project.destinations.MainDestination
import org.example.project.destinations.ProcessingDestination
import org.example.project.destinations.ResultDestination
import org.example.project.screens.AboutScreen
import org.example.project.screens.MainScreen
import org.example.project.screens.ProcessingScreen
import org.example.project.screens.ResultScreen

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            darkColorScheme()
        } else {
            lightColorScheme()
        }
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = MainDestination,
                modifier = Modifier.fillMaxSize().safeDrawingPadding()
            ) {
                composable<MainDestination> {
                    MainScreen(
                        onNavigateAbout = {
                            navController.navigate(AboutDestination)
                        },
                        onNavigateProcessing = {
                            navController.navigate(ProcessingDestination)
                        }
                    )
                }

                composable<AboutDestination> {
                    AboutScreen(
                        onNavigateBack = {
                            if (!navController.popBackStack()) {
                                navController.navigate(MainDestination)
                            }
                        }
                    )
                }

                composable<ProcessingDestination> {
                    ProcessingScreen(
                        onNavigateBack = {
                            if (!navController.popBackStack()) {
                                navController.navigate(MainDestination)
                            }
                        },
                        onNavigateResult = {
                            if (!navController.popBackStack()) {
                                navController.navigate(MainDestination)
                            }

                            navController.navigate(ResultDestination)
                        }
                    )
                }

                composable<ResultDestination> {
                    ResultScreen(
                        onNavigateBack = {
                            if (!navController.popBackStack()) {
                                navController.navigate(MainDestination)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Preview(
    showSystemUi = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun AppPreview() {
    App()
}
