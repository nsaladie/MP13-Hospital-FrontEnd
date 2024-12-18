package com.example.hospitalfrontend.ui.nurses.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.hospitalfrontend.R
import com.example.hospitalfrontend.ui.nurses.viewmodels.NurseViewModel
import com.example.hospitalfrontend.ui.theme.HospitalFrontEndTheme

class FindByName : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HospitalFrontEndTheme {
                FindByName()
            }
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    HospitalFrontEndTheme {
        FindByName()
    }
}

@Composable
fun OnBoardingScreen(onContinueClicked: () -> Unit, message: String) {
    Button(onClick = onContinueClicked) {
        Text(text = message)
    }
}

@Composable
fun FindScreen(navController: NavController, nurseViewModel: NurseViewModel) {
    var nurseName = rememberSaveable { mutableStateOf("") }
    var result = rememberSaveable { mutableStateOf("") }
    val nurses = arrayOf("Alex", "Noemi", "Dafne")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        // Back button at the top-right
        IconButton(
            onClick = {
                navController.popBackStack()
            }, modifier = Modifier
                .align(Alignment.TopEnd) // Position at top-right
                .zIndex(1f) // Ensures this is above LazyColumn
        ) {
            Icon(
                imageVector = Icons.Filled.Close, // Example icon
                contentDescription = "Close Button", tint = colorResource(id = R.color.colorText)
            )
        }
    }
    Column(modifier = Modifier.fillMaxHeight(fraction = 1f)) {
        Text(text = "Find Screen", color = Color.Green)
        androidx.compose.material3.TextField(value = nurseName.value,
            onValueChange = { nurseName.value = it },
            label = {
                Text("Enter a Nurse Name")
            })
        Button(
            onClick = {
                result.value = if (nurses.any { it.equals(nurseName.value) }) {
                    "Nurse found!"
                } else {
                    "Not Found"
                }
            },
        ) {
            Text("Find the Nurse")
        }
        Text(text = result.value, color = Color.Green)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier) {

    }
    Text(
        text = "Hello $name!", modifier = modifier
    )
    Text(text = "Exemple test", color = Color.Yellow)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HospitalFrontEndTheme {
        Greeting("Android")
    }
}
