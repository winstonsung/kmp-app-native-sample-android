package org.example.project.models

import androidx.annotation.StringRes

data class LanguageOptionItem(
    val languageTag: String,
    @StringRes val localNameResId: Int,
    @StringRes val translatedNameResId: Int
)
