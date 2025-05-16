package org.example.project.composables

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import androidx.core.os.LocaleListCompat
import org.example.project.R
import org.example.project.models.LanguageOptionItem

@Composable
fun LanguageDropdownMenu(
    expanded: Boolean = false,
    onHideDropdownMenu: () -> Unit = {}
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onHideDropdownMenu
    ) {
        val languageOptions = listOf(
            LanguageOptionItem(
                languageTag = "x-default",
                localNameResId = R.string.lang_translated_name_x_default,
                translatedNameResId = R.string.lang_translated_name_x_default
            ),
            LanguageOptionItem(
                languageTag = "en-US",
                localNameResId = R.string.lang_local_name_en_us,
                translatedNameResId = R.string.lang_translated_name_en_us
            ),
            LanguageOptionItem(
                languageTag = "zh-Hant-TW",
                localNameResId = R.string.lang_local_name_zh_hant_tw,
                translatedNameResId = R.string.lang_translated_name_zh_hant_tw
            )
        )

        languageOptions.fastForEach { option ->
            LanguageDropdownMenuItem(
                localNameResId = option.localNameResId,
                translatedNameResId = option.translatedNameResId,
                isSelected = if (option.languageTag == "x-default") {
                    AppCompatDelegate.getApplicationLocales() == LocaleListCompat.getEmptyLocaleList()
                } else {
                    AppCompatDelegate.getApplicationLocales() == LocaleListCompat.forLanguageTags(option.languageTag)
                }
            ) {
                AppCompatDelegate.setApplicationLocales(
                    if (option.languageTag == "x-default") {
                        LocaleListCompat.getEmptyLocaleList()
                    } else {
                        LocaleListCompat.forLanguageTags(option.languageTag)
                    }
                )
                onHideDropdownMenu()
            }
        }
    }
}
