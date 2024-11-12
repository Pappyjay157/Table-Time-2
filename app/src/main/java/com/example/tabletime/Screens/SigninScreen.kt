package com.example.tabletime.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tabletime.AuthViewModel

@Composable
fun SigninScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // State variables for email and password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEAE6)) // Light beige background color
    ) {
        // Circle decoration in the top left corner


        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sign-in Title
            Text(text = "Sign in", fontSize = 35.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(20.dp))

            // Email Input Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Enter your email") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(15.dp))

            // Password Input Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Enter password") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Sign-In Button
            Button(
                onClick = {
                    // Handle sign-in logic here
                    // TODO
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(Color(0xFFEFEAE6)) // Button color
            ) {
                Text(text = "Sign in", fontSize = 18.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Sign-up Redirect Text
            TextButton(onClick = {
                navController.navigate("Signup")
            }) {
                Text(text = "Don't have an account? Sign up", color = Color(0xFFE45555))
            }
        }
    }
}
