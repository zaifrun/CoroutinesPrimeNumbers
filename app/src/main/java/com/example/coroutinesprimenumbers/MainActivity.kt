package com.example.coroutinesprimenumbers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coroutinesprimenumbers.ui.theme.CoroutinesPrimeNumbersTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesPrimeNumbersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var scope = rememberCoroutineScope()
                    var counter by remember {
                        mutableStateOf(0)
                    }
                    var factorNumberString by remember {
                        mutableStateOf("")
                    }
                    var resultFactors by remember {
                        mutableStateOf(mutableListOf<Long>())
                    }
                    var factorNumber by remember {
                        mutableStateOf(0L)
                    }
                    var errorMessage by remember {
                        mutableStateOf("Enter a number to factorize")
                    }
                    // Greeting("Android")
                    Column {
                        Button(
                            onClick = {
                                counter++
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Counter: ${counter}"
                            )

                        }
                        Button(
                            onClick = {
                                      scope.launch(context = Dispatchers.Default) {
                                          if (factorNumber!=0L)
                                          {
                                              resultFactors = factorize(factorNumber)
                                          }
                                      }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp)
                        ) {
                            Text(
                                text = "factorize"
                            )

                        }
                        OutlinedTextField(
                            value = factorNumberString,
                            textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),

                            onValueChange = {
                                try {
                                    if (it.isNotEmpty()) {
                                        factorNumber = it.toLong()
                                        errorMessage = ""
                                    }
                                    else {
                                        factorNumber = 0
                                        errorMessage = "Enter a valid number"
                                    }
                                    factorNumberString = it
                                } catch (e: NumberFormatException) {
                                     //no change - no updates.
                                }
                            },
                            readOnly = false,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )
                        Text (text = errorMessage,
                            color = Color.Red,
                            modifier = Modifier.padding(5.dp))
                        ShowPrimeFactors(resultFactors)
                    }
                }
            }
        }
    }
}

//composable for showing a list of prime numbers using a lazy list

@Composable
fun ShowPrimeFactors(list: MutableList<Long> = mutableListOf())
{
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) { number ->
            Row()
            {
                Text(
                    text = number.toString(), fontSize = 20.sp,
                    modifier = Modifier.padding(6.dp)
                )
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
    CoroutinesPrimeNumbersTheme {
        Greeting("Android")
    }
}

fun factorize(n: Long): MutableList<Long> {
    val factors = mutableListOf<Long>()
    var remaining = n
    var divisor = 2L
    while (remaining > 1) {
        while (remaining % divisor == 0L) {
            factors.add(divisor)
            remaining /= divisor
        }
        divisor++
    }
    return factors
}