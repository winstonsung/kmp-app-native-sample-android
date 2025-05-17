package org.example.project

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.project.composables.ModalDrawerSheet
import org.example.project.destinations.AboutDestination
import org.example.project.destinations.MainDestination
import org.example.project.destinations.MapDestination
import org.example.project.destinations.ProcessingDestination
import org.example.project.destinations.ResultDestination
import org.example.project.screens.AboutScreen
import org.example.project.screens.MainScreen
import org.example.project.screens.MapScreen
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
            val scope: CoroutineScope = rememberCoroutineScope()

            val navController: NavHostController = rememberNavController()

            val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet(
                        navController = navController,
                        onMenuClose = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onNavigateMain = {
                            scope.launch {
                                drawerState.close()
                            }

                            scope.launch {
                                navController.navigate(route = MainDestination) {
                                    popUpTo(route = MainDestination)

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        onNavigateMap = {
                            scope.launch {
                                drawerState.close()
                            }

                            scope.launch {
                                navController.navigate(MapDestination) {
                                    popUpTo(route = MainDestination)

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        onNavigateAbout = {
                            scope.launch {
                                drawerState.close()
                            }

                            scope.launch {
                                navController.navigate(AboutDestination) {
                                    popUpTo(route = MainDestination)

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                },
                drawerState = drawerState,
                gesturesEnabled = true,
                content = {
                    NavHost(
                        navController = navController,
                        startDestination = MainDestination,
                        modifier = Modifier.fillMaxSize().safeDrawingPadding()
                    ) {
                        composable<MainDestination> {
                            MainScreen(
                                onMenuOpen = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                },
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
                                onMenuOpen = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            )
                        }

                        composable<MapDestination> {
                            MapScreen(
                                onMenuOpen = {
                                    scope.launch {
                                        drawerState.open()
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
            )
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
