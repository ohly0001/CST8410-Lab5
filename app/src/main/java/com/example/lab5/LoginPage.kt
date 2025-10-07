package com.example.lab5

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lab5.ui.theme.Lab5Theme


class LoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Surface(modifier = Modifier.padding(40.dp))
                {
                    Login()
                }
            }
        }
    }
}

@Composable
fun Login() {
    val uname = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }
    val context = LocalActivity.current
    val nextPage = Intent(context, MainPage::class.java)

    Column(){
        Text("Login")
        TextField(
            value = uname.value,
            onValueChange = { v -> uname.value = v },
            label = { Text("Username") },
            singleLine = true
        )
        TextField(
            value = pass.value,
            onValueChange = { v -> pass.value = v },
            label = { Text("Password") },
            singleLine = true
        )
        Row() {
            Button(
                onClick = {
                    Repository.getInstance().uname = uname.value
                    context?.startActivity(nextPage)
                },
                enabled = uname.value.isNotBlank() && pass.value.isNotBlank(),
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF4CAF50),
                    disabledContainerColor = Color(0xFFABB1AA),
                    disabledContentColor = Color.White
                )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Login,
                    contentDescription = "Login"
                )
                Text("Login")
            }
            Button(
                onClick = {
                    context?.finish()
                },
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFFF44336),
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                )
            ) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Back")
                Text("Back")
            }
            Button(
                onClick = {
                    uname.value = ""
                    pass.value = ""
                },
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF00BCD4),
                    disabledContainerColor = Color(0xFFC3C4C4),
                    disabledContentColor = Color.White
                ),
                enabled = uname.value.isNotBlank() || pass.value.isNotBlank()
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Clear")
                Text("Clear")
            }
        }
    }
}