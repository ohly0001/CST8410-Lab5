package com.example.lab5

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
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
import androidx.core.net.toUri
import com.example.lab5.ui.theme.Lab5Theme

class MainPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Surface(modifier = Modifier.padding(40.dp))
                {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableListOf("", "", "") }
    val context = LocalActivity.current
    val phoneRegex = "^(\\(\\d{3}\\) \\d{3} \\d{4})|(\\d{10})\\$".toRegex()
    val emailRegex = "^(?!.*\\.\\.)[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$".toRegex()

    Column {
        Text("Welcome Back ${Repository.getInstance().uname}!")
        Row{
            TextField(
                value = phone.value,
                onValueChange = { v ->
                    phone.value = v
                },
                placeholder = { Text("(123) 456 7890") },
                label = { Text("Phone Number") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${phone.value}".toUri()
                    }
                    phone.value = ""
                    if (intent.resolveActivity(context!!.packageManager) != null) {
                        context.startActivity(intent)
                    }
                },
                enabled = phoneRegex.matches(phone.value)
            ) {
                Icon(
                    Icons.Filled.Call,
                    contentDescription = "Make Call")
                Text("Call")
            }
        }
        Column {
            TextField(
                value = email[0],
                onValueChange = { v ->
                    email[0] = v
                },
                placeholder = { Text("you@mail.org") },
                label = { Text("Email Address") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = email[1],
                onValueChange = { v ->
                    email[1] = v
                },
                label = { Text("Subject") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = email[2],
                onValueChange = { v ->
                    email[2] = v
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(email[0]))
                        putExtra(Intent.EXTRA_SUBJECT, email[1])
                        putExtra(Intent.EXTRA_TEXT, email[2])
                    }
                    email[0] = ""
                    email[1] = ""
                    email[2] = ""
                    if (intent.resolveActivity(context!!.packageManager) != null) {
                        context.startActivity(intent)
                    }
                },
                enabled = emailRegex.matches(email[0])
            ) {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = "Make Call")
                Text("Call")
            }
        }
        Row{
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
                    Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout")
                Text("Logout")
            }
            Button(
                onClick = {
                    context?.finish()
                },
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF00BCD4),
                    disabledContainerColor = Color(0xFFC3C4C4),
                    disabledContentColor = Color.White
                )
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Clear")
                Text("Clear")
            }
        }
    }
}