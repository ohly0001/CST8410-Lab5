package com.example.lab5

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lab5.ui.theme.Lab5Theme

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Surface(modifier = Modifier.padding(40.dp))
                {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    val context = LocalActivity.current
    val nextPage = Intent(context, LoginPage::class.java)

    Button(
    onClick = {
            context?.startActivity(nextPage) //context might be null
        },
        colors = ButtonColors(
            contentColor = Color.White,
            containerColor = Color(0xFF4CAF50),
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        )
    ) {
        Icon(
            Icons.AutoMirrored.Filled.Login,
            contentDescription = "Back"
        )
        Text("Login")
    }
}