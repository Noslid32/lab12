package com.example.colormixer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableFloatStateOf


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorMixerApp()
        }
    }
}


@Composable
fun ColorMixerApp() {
    var red by rememberSaveable { mutableFloatStateOf(0f) }
    var green by rememberSaveable { mutableFloatStateOf(0f) }
    var blue by rememberSaveable { mutableFloatStateOf(0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Color Mixer",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(red / 255f, green / 255f, blue / 255f)
                ) {}
            }

            Spacer(modifier = Modifier.height(16.dp))


            ColorSlider("Red", red) { red = it }
            ColorSlider("Green", green) { green = it }
            ColorSlider("Blue", blue) { blue = it }


            val hexColor = String.format("#%02X%02X%02X", red.toInt(), green.toInt(), blue.toInt())
            Text(
                text = "HEX: $hexColor",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )


            Button(
                onClick = { red = 0f; green = 0f; blue = 0f },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Reset Colors")
            }
        }
    }
}

@Composable
fun ColorSlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    val sliderColor = when (label) {
        "Red" -> Color.Red
        "Green" -> Color.Green
        "Blue" -> Color.Blue
        else -> Color.Gray
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "$label: ${value.toInt()}", style = MaterialTheme.typography.bodyLarge)
        Slider(
            value = value,
            onValueChange = { onValueChange(it) },
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = sliderColor,
                activeTrackColor = sliderColor,
                inactiveTrackColor = sliderColor.copy(alpha = 0.3f)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorMixerPreview() {
    ColorMixerApp()
}
