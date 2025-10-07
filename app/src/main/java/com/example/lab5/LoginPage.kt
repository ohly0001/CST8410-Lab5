package com.example.lab5

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lab5.ui.theme.Lab5Theme

class LoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Surface(modifier = Modifier.padding(40.dp)) {
                    Login()
                }
            }
        }
    }
}

@Composable
fun Login() {
    val user = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }

    val context = LocalActivity.current
    val nextPage = Intent(context, ProfilePage::class.java)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Login", style = MaterialTheme.typography.titleLarge)

        TextField(
            value = user.value,
            onValueChange = { user.value = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = pass.value,
            onValueChange = { pass.value = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Login Button
            Button(
                onClick = {
                    Repository.getInstance().user = user.value
                    context?.startActivity(nextPage)
                },
                enabled = user.value.isNotBlank() && pass.value.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFABB1AA),
                    disabledContentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.AutoMirrored.Filled.Login, contentDescription = "Login")
            }

            // Back Button
            Button(
                onClick = { context?.finish() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336),
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.Home, contentDescription = "Back")
            }

            // Clear Button
            Button(
                onClick = {
                    user.value = ""
                    pass.value = ""
                },
                enabled = user.value.isNotBlank() || pass.value.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFC3C4C4),
                    disabledContentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Clear")
            }
        }
    }
}
