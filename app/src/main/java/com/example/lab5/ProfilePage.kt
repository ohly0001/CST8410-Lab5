package com.example.lab5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

class ProfilePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Surface(modifier = Modifier.padding(40.dp)) {
                    Profile(intent.data)
                }
            }
        }
    }
}

@Composable
fun Profile(dldata: Uri?) {
    val phone = remember { mutableStateOf("") }
    val emailAddress = remember { mutableStateOf("") }
    val emailSubject = remember { mutableStateOf("") }
    val emailBody = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }

    // for testing, using \& instead of & to escape it,
    // otherwise it will run as a background job and not apply all the params
    dldata?.let { d ->
        d.getQueryParameter("phone")?.let { qp -> phone.value = qp }
        d.getQueryParameter("email")?.let { qp -> emailAddress.value = qp }
        d.getQueryParameter("address")?.let { qp -> address.value = qp }
    }

    val context = LocalActivity.current

    val phoneRegex = "^(\\(\\d{3}\\) \\d{3} \\d{4}|\\d{10})$".toRegex()
    val emailRegex = "^(?!.*\\.\\.)[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$".toRegex()

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Welcome Back ${Repository.getInstance().user}!")

        // Phone Section
        Text("Call")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                placeholder = { Text("(123) 456 7890") },
                label = { Text("Phone Number") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${phone.value}".toUri()
                    }
                    if (intent.resolveActivity(context!!.packageManager) != null) {
                        context.startActivity(intent)
                        phone.value = ""
                    }
                },
                enabled = phoneRegex.matches(phone.value)
            ) {
                Icon(Icons.Filled.Call, contentDescription = "Make Call")
                Spacer(Modifier.width(8.dp))
                Text("Dial")
            }
        }

        // Email Section
        Text("Email")
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = emailAddress.value,
                onValueChange = { emailAddress.value = it },
                placeholder = { Text("you@mail.org") },
                label = { Text("Email Address") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = emailSubject.value,
                onValueChange = { emailSubject.value = it },
                label = { Text("Subject") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = emailBody.value,
                onValueChange = { emailBody.value = it },
                label = { Text("Body") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "mailto:".toUri()
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress.value))
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject.value)
                        putExtra(Intent.EXTRA_TEXT, emailBody.value)
                    }
                    if (intent.resolveActivity(context!!.packageManager) != null) {
                        context.startActivity(intent)
                        emailAddress.value = ""
                        emailSubject.value = ""
                        emailBody.value = ""
                    }
                },
                enabled = emailRegex.matches(emailAddress.value)
            ) {
                Icon(Icons.Filled.Email, contentDescription = "Send Email")
                Spacer(Modifier.width(8.dp))
                Text("Send")
            }
        }

        // Maps Section
        Text("Maps")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = address.value,
                onValueChange = { address.value = it },
                placeholder = { Text("123 Main St") },
                label = { Text("Address") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = "geo:0,0?q=${address.value}".toUri()
                    }
                    if (intent.resolveActivity(context!!.packageManager) != null) {
                        context.startActivity(intent)
                        address.value = ""
                    }
                },
                enabled = address.value.isNotBlank()
            ) {
                Icon(Icons.Filled.MyLocation, contentDescription = "Locate")
                Spacer(Modifier.width(8.dp))
                Text("Locate")
            }
        }

        // Logout and Clear
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { context?.finish() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336),
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                Spacer(Modifier.width(8.dp))
                Text("Logout")
            }
            Button(
                onClick = {
                    phone.value = ""
                    emailAddress.value = ""
                    emailSubject.value = ""
                    emailBody.value = ""
                    address.value = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFC3C4C4),
                    disabledContentColor = Color.White
                ),
                enabled = phone.value.isNotBlank() || emailAddress.value.isNotBlank() || address.value.isNotBlank() || emailSubject.value.isNotBlank() || emailBody.value.isNotBlank()
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Clear")
                Spacer(Modifier.width(8.dp))
                Text("Clear")
            }
        }
    }
}