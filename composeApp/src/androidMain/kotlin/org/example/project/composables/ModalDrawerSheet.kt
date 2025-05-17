package org.example.project.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.project.R
import org.example.project.destinations.AboutDestination
import org.example.project.destinations.MainDestination
import org.example.project.destinations.MapDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawerSheet(
    navController: NavHostController,
    onMenuClose: () -> Unit = {},
    onNavigateMain: () -> Unit = {},
    onNavigateMap: () -> Unit = {},
    onNavigateAbout: () -> Unit = {}
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    ModalDrawerSheet {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.menu_title))
            },
            navigationIcon = {
                IconButton(onClick = onMenuClose) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.menu_action_close)
                    )
                }
            },
        )

        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
                .fillMaxWidth(),
        ) {
            NavigationDrawerItem(
                label = {
                    Text(text = "Home")
                },
                selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(MainDestination::class)
                } == true,
                onClick = onNavigateMain,
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.hierarchy?.any {
                            it.hasRoute(MainDestination::class)
                        } == true) {
                            Icons.Filled.Home
                        } else {
                            Icons.Outlined.Home
                        },
                        contentDescription = null
                    )
                }
            )

            NavigationDrawerItem(
                label = {
                    Text(text = stringResource(id = R.string.menu_item_map))
                },
                selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(MapDestination::class)
                } == true,
                onClick = onNavigateMap,
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.hierarchy?.any {
                            it.hasRoute(MapDestination::class)
                        } == true) {
                            Icons.Filled.Map
                        } else {
                            Icons.Outlined.Map
                        },
                        contentDescription = null
                    )
                }
            )

            NavigationDrawerItem(
                label = {
                    Text(text = stringResource(id = R.string.menu_item_about))
                },
                selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(AboutDestination::class)
                } == true,
                onClick = onNavigateAbout,
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.hierarchy?.any {
                            it.hasRoute(AboutDestination::class)
                        } == true) {
                            Icons.Filled.Info
                        } else {
                            Icons.Outlined.Info
                        },
                        contentDescription = null
                    )
                }
            )
        }
    }
}
