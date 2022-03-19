package com.example.androidprotogenerator

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidprotogenerator.data.ModeOption
import com.example.androidprotogenerator.ui.theme.AndroidProtoGeneratorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidProtoGeneratorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun DropdownDemo() {
    var expanded by remember { mutableStateOf(false) }
    val items = ModeOption.Mode.values().filterNot { it == ModeOption.Mode.UNRECOGNIZED }
    var selectedIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .wrapContentSize(
                    Alignment.Center
                )
        ) {
            Text(
                items[selectedIndex].name,
                modifier = Modifier
                    .clickable(onClick = { expanded = true })
                    .graphicsLayer(clip = true, shape = RoundedCornerShape(4.dp))
                    .background(Color.LightGray)
                    .padding(8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(
                        Color.Red
                    )
            ) {
                items.forEachIndexed { index, selected ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) {
                        Text(text = selected.name)
                    }
                }
            }
        }
        MyButton("Save to Internal File") {
            Toast.makeText(context,"Saved ${items[selectedIndex].name} to file", Toast.LENGTH_SHORT).show()
            ProtoFile.writeToFile(context, items[selectedIndex])
        }
        MyButton("Read from Internal File") {
            val data = ProtoFile.readFromFile(context)
            setAndToastReadData(data, context) { selectedIndex = it }
        }
        MyButton("Save to External File") {
            Toast.makeText(context, "Saved ${items[selectedIndex].name} to file", Toast.LENGTH_SHORT).show()
            ProtoFile.writeToExternalFile(context, items[selectedIndex])
        }
        MyButton("Read from External File") {
            val data = ProtoFile.readFromExternalFile(context)
            setAndToastReadData(data, context) { selectedIndex = it }
        }
    }
}

private fun setAndToastReadData(
    data: ModeOption?,
    context: Context,
    action: (Int) -> Unit
) {
    if (data != null) {
        Toast.makeText(
            context,
            "From File ${data.mode}", Toast.LENGTH_SHORT
        ).show()
        action(data.modeValue)
    } else {
        Toast.makeText(
            context, "No data read", Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
private fun MyButton(
    caption: String,
    action: () -> Unit
) {
    Spacer(Modifier.height(8.dp))
    Button(onClick = action) {
        Text(caption)
    }
}


@Composable
fun Greeting(name: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DropdownDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidProtoGeneratorTheme {
        Greeting("Android")
    }
}