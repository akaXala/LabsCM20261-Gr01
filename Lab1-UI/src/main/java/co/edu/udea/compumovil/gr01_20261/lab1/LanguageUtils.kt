package co.edu.udea.compumovil.gr01_20261.lab1

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat

@Composable
fun LanguageSwitcher() {
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("es" to stringResource(R.string.spanish), "en" to stringResource(R.string.english))

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.Language, contentDescription = stringResource(R.string.language_option))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Log.i("LanguageSwitcher", "Languages: $languages")
            languages.forEach { (code, name) ->
                Log.i("LanguageSwitcher", "Code: $code, Name: $name")
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        Log.i("LanguageSwitcher", "Setting language to $code")
                        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(code)
                        AppCompatDelegate.setApplicationLocales(appLocale)
                        expanded = false
                    }
                )
            }
        }
    }
}
