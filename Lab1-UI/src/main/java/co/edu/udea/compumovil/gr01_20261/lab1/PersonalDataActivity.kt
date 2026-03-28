package co.edu.udea.compumovil.gr01_20261.lab1

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr01_20261.lab1.ui.theme.Labs20261Gr01Theme
import java.text.SimpleDateFormat
import java.util.*

class PersonalDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Labs20261Gr01Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PersonalDataScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    var nombre by rememberSaveable { mutableStateOf("") }
    var apellido by rememberSaveable { mutableStateOf("") }
    var sexoSelected by rememberSaveable { mutableStateOf("Hombre") }
    var fechaTexto by rememberSaveable { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    
    val opcionesEscolaridad = listOf(
        stringResource(R.string.education_primary),
        stringResource(R.string.education_secondary),
        stringResource(R.string.education_university),
        stringResource(R.string.education_other)
    )
    var escolaridadSelected by rememberSaveable { mutableStateOf("") }
    if (escolaridadSelected.isEmpty() && opcionesEscolaridad.isNotEmpty()) {
        escolaridadSelected = opcionesEscolaridad[0]
    }

    var nombreError by rememberSaveable { mutableStateOf(false) }
    var apellidoError by rememberSaveable { mutableStateOf(false) }
    var fechaError by rememberSaveable { mutableStateOf(false) }

    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                fechaTexto = dateFormatter.format(calendar.time)
                fechaError = false
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnCancelListener { showDatePicker = false }
            show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.personal_info_title),
                style = MaterialTheme.typography.headlineMedium
            )
            LanguageSwitcher()
        }

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomTextField(
                    value = nombre,
                    onValueChange = { nombre = it; nombreError = false },
                    label = stringResource(R.string.first_names),
                    icon = Icons.Default.Person,
                    isError = nombreError,
                    modifier = Modifier.weight(1f),
                    imeAction = ImeAction.Next
                )
                CustomTextField(
                    value = apellido,
                    onValueChange = { apellido = it; apellidoError = false },
                    label = stringResource(R.string.last_names),
                    icon = Icons.Default.Person,
                    isError = apellidoError,
                    modifier = Modifier.weight(1f),
                    imeAction = ImeAction.Next
                )
            }
        } else {
            CustomTextField(
                value = nombre,
                onValueChange = { nombre = it; nombreError = false },
                label = stringResource(R.string.first_names),
                icon = Icons.Default.Person,
                isError = nombreError,
                imeAction = ImeAction.Next
            )
            CustomTextField(
                value = apellido,
                onValueChange = { apellido = it; apellidoError = false },
                label = stringResource(R.string.last_names),
                icon = Icons.Default.Person,
                isError = apellidoError,
                imeAction = ImeAction.Next
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text(text = stringResource(R.string.gender))

            RadioButton(
                selected = (sexoSelected == "Hombre"),
                onClick = { sexoSelected = "Hombre" }
            )
            Text(text = stringResource(R.string.male))

            RadioButton(
                selected = (sexoSelected == "Mujer"),
                onClick = { sexoSelected = "Mujer" }
            )
            Text(text = stringResource(R.string.female))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (fechaError) 2.dp else 0.dp,
                    color = if (fechaError) MaterialTheme.colorScheme.error else Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Default.DateRange, contentDescription = null)
            Text(
                text = if (fechaTexto.isEmpty()) stringResource(R.string.birth_date_label) else fechaTexto,
                modifier = Modifier.weight(1f),
                color = if (fechaError) MaterialTheme.colorScheme.error else Color.Unspecified
            )
            Button(onClick = { showDatePicker = true }) {
                Text(text = stringResource(R.string.change_button))
            }
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = escolaridadSelected,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.education_level)) },
                leadingIcon = { Icon(Icons.Default.List, contentDescription = null) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opcionesEscolaridad.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            escolaridadSelected = opcion
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                nombreError = nombre.isBlank()
                apellidoError = apellido.isBlank()
                fechaError = fechaTexto.isBlank()

                if (nombreError || apellidoError || fechaError) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.mandatory_fields_error),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.i("Main Activity", "Información Personal:")
                    Log.i("Main Activity", "$nombre $apellido")
                    Log.i("Main Activity", sexoSelected)
                    Log.i("Main Activity", "Nacio el $fechaTexto")
                    Log.i("Main Activity", escolaridadSelected)

                    val intent = android.content.Intent(context, ContactDataActivity::class.java)
                    context.startActivity(intent)
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.next_button))
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isError: Boolean,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        )
    )
}
