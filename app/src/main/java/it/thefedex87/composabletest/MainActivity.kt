package it.thefedex87.composabletest

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.thefedex87.composabletest.ui.theme.ComposableTestTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Log.d("COMPOSABLE_TEST", "Recompose Column")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        val painter = painterResource(id = R.drawable.android_jetpack_header)
                        val title = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Green,
                                    fontSize = 20.sp,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("E")
                            }
                            append("xample")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Green,
                                    fontSize = 20.sp,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("O")
                            }
                            append("f")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Green,
                                    fontSize = 20.sp,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("J")
                            }
                            append("etpack")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Green,
                                    fontSize = 20.sp,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("C")
                            }
                            append("ompose")
                        }
                        val description = "An Example of jetpack compose"
                        ExampleCard(painter = painter, title = title, description = description)
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Log.d("COMPOSABLE_TEST", "Recompose Row")
                        var color by remember { mutableStateOf(Color.Red) }
                        ColoredBox(
                            color = color,
                            r = color.red.toString(),
                            g = color.green.toString(),
                            b = color.blue.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        Button(onClick = {
                            color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f
                            )
                        }, modifier = Modifier.padding(6.dp)) {
                            Text(text = "Change Color")
                        }
                    }

                    var greetingName by remember {
                        mutableStateOf("")
                    }
                    GreetingEditText(value = greetingName,
                        onValueChanged = {
                            greetingName = it
                        }, onConfirm = {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Hello $greetingName")
                            }
                        })

                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)) {
                        items(100) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Item $it",
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = 18.sp, color = Color.Blue)
                            )
                        }
                    }

                    var sideEffectTestState by remember {
                        mutableStateOf(0)
                    }
                    SideEffectTestComposable(sideEffectTestState.toString()) {
                        sideEffectTestState++
                    }
                }
            }
        }
    }
}

@Composable
fun ColoredBox(
    color: Color,
    r: String,
    g: String,
    b: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "R: $r")
            Text(text = "G: $g")
            Text(text = "B: $b")
        }
    }
}

@Composable
fun ExampleCard(
    painter: Painter,
    title: AnnotatedString,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = description,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}

@Composable
fun GreetingEditText(
    value: String,
    onValueChanged: (String) -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(modifier = Modifier
            .weight(1f)
            .padding(8.dp),
            value = value,
            label = {
                Text(text = "Enter name")
            },
            onValueChange = {
                onValueChanged(it)
            })
        Button(onClick = onConfirm) {
            Text("Greet me")
        }
    }
}


var i = 0
@Composable
fun SideEffectTestComposable(
    value: String,
    onValueChanged: () -> Unit
) {
    SideEffect {
        i++
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Value of i is $i")
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = value, modifier = Modifier.weight(1f))
            Button(onClick = onValueChanged) {
                Text(text = "Increment")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposableTestTheme {
        Greeting("Android")
    }
}