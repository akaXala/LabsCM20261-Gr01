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
            label = { Text("Telefono *") },
            leadingIcon = { Icon(Icons.Default.Call, contentDescription = null) },
            isError = telefonoError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it; direccionError = false },
            label = { Text("Dirección *") },
            leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
            isError = direccionError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; emailError = false },
            label = { Text("Email *") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            isError = emailError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = pais,
                    onValueChange = { pais = it },
                    label = { Text("País") },
                    leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = ciudad,
                    onValueChange = { ciudad = it },
                    label = { Text("Ciudad") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            OutlinedTextField(
                value = pais,
                onValueChange = { pais = it },
                label = { Text("País") },
                leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ciudad,
                onValueChange = { ciudad = it },
                label = { Text("Ciudad") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
        }

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