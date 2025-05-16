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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.composables.LanguageDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateAbout: () -> Unit = {},
    onNavigateProcessing: () -> Unit = {}
) {
    val pickMedia: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> =
        rememberLauncherForActivityResult(contract = PickVisualMedia()) { uri ->
            if (uri != null) {
                onNavigateProcessing()
            }
        }

    var shouldShowOptionMenu: Boolean by remember { mutableStateOf(value = false) }

    var shouldShowLanguagePicker: Boolean by remember { mutableStateOf(value = false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { shouldShowOptionMenu = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(id = R.string.switch_language)
                        )
                    }

                    DropdownMenu(
                        expanded = shouldShowOptionMenu,
                        onDismissRequest = { shouldShowOptionMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Column(modifier = Modifier.padding(horizontal = 2.dp, vertical = 10.dp)) {
                                    Text(
                                        text = stringResource(id = R.string.switch_language),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            },
                            onClick = {
                                shouldShowOptionMenu = false
                                shouldShowLanguagePicker = true
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Language,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Column(modifier = Modifier.padding(horizontal = 2.dp, vertical = 10.dp)) {
                                    Text(
                                        text = stringResource(id = R.string.about),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            },
                            onClick = {
                                shouldShowOptionMenu = false
                                onNavigateAbout()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = null
                                )
                            }
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
