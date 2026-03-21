package co.edu.udea.compumovil.gr01_20261.lab1

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr01_20261.lab1.ui.theme.Labs20261Gr01Theme

class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Labs20261Gr01Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ContactDataScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    var telefono by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var pais by rememberSaveable { mutableStateOf("") }
    var ciudad by rememberSaveable { mutableStateOf("") }

    var telefonoError by rememberSaveable { mutableStateOf(false) }
    var direccionError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }

    // Estado para el autocompletado de país
    var expandedPais by remember { mutableStateOf(false) }
    val paisesLatam = listOf("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica", "Cuba", "Ecuador", "El Salvador", "Guatemala", "Honduras", "México", "Nicaragua", "Panamá", "Paraguay", "Perú", "Puerto Rico", "República Dominicana", "Uruguay", "Venezuela")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Información de contacto",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it; telefonoError = false },
            label = { Text("Teléfono *") },
            leadingIcon = { Icon(Icons.Default.Call, contentDescription = null) },
            isError = telefonoError,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it; direccionError = false },
            label = { Text("Dirección *") },
            leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
            isError = direccionError,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; emailError = false },
            label = { Text("Email *") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            isError = emailError,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        ExposedDropdownMenuBox(
            expanded = expandedPais,
            onExpandedChange = { expandedPais = !expandedPais },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = pais,
                onValueChange = {
                    pais = it
                    expandedPais = it.isNotEmpty()
                },
                label = { Text("País *") },
                leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPais) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            val filtrados = paisesLatam.filter { it.contains(pais, ignoreCase = true) }

            if (filtrados.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expandedPais,
                    onDismissRequest = { expandedPais = false }
                ) {
                    filtrados.forEach { sugerencia ->
                        DropdownMenuItem(
                            text = { Text(sugerencia) },
                            onClick = {
                                pais = sugerencia
                                expandedPais = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = ciudad,
            onValueChange = { ciudad = it },
            label = { Text("Ciudad") },
            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        Button(
            onClick = {
                telefonoError = telefono.isBlank()
                direccionError = direccion.isBlank()
                emailError = email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

                if (telefonoError || direccionError || emailError) {
                    val msg = if (emailError && email.isNotBlank()) "Email no válido" else "Complete los campos obligatorios (*)"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                } else {
                    Log.i("Main Activity", "Información de contacto:")
                    Log.i("Main Activity", "Teléfono: $telefono")
                    Log.i("Main Activity", "Dirección: $direccion")
                    Log.i("Main Activity", "Email: $email")
                    if (pais.isNotBlank()) Log.i("Main Activity", "País: $pais")
                    if (ciudad.isNotBlank()) Log.i("Main Activity", "Ciudad: $ciudad")
                }
            },
            modifier = Modifier.align(Alignment.End).padding(top = 16.dp)
        ) {
            Text(text = "Siguiente")
        }
    }
}
