package org.example.project.screens

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.R
import org.example.project.composables.LanguageDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onMenuOpen: () -> Unit = {},
    onNavigateAbout: () -> Unit = {},
    onNavigateProcessing: () -> Unit = {}
) {
    val pickMedia: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> =
        rememberLauncherForActivityResult(contract = PickVisualMedia()) { uri ->
            if (uri != null) {
                onNavigateProcessing()
            }
        }

    var shouldShowLanguagePicker: Boolean by remember { mutableStateOf(value = false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    IconButton(onClick = onMenuOpen) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(id = R.string.menu_action_open)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { shouldShowLanguagePicker = true }) {
                        Icon(
                            imageVector = Icons.Outlined.Language,
                            contentDescription = stringResource(id = R.string.switch_language)
                        )
                    }

                    LanguageDropdownMenu(
                        expanded = shouldShowLanguagePicker,
                        onHideDropdownMenu = { shouldShowLanguagePicker = false }
                    )
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(paddingValues = innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        pickMedia.launch(
                            input = PickVisualMediaRequest(
                                mediaType = PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.select_photo),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }
    )
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
fun MainScreenPreview() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            darkColorScheme()
        } else {
            lightColorScheme()
        }
    ) {
        Surface {
            MainScreen()
        }
    }
}
