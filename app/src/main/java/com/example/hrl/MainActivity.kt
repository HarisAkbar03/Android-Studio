package com.example.hrl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hrl.ui.theme.HrlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HrlTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppContent()
                }
            }
        }
    }
}

// Main content with navigation
@Composable
fun AppContent() {
    var isSplashScreenVisible by remember { mutableStateOf(true) }

    if (isSplashScreenVisible) {
        SplashScreen(onGetStartedClick = { isSplashScreenVisible = false }) // Change here
    } else {
        LoginRegisterScreen()
    }
}

// Splash Screen
@Composable
fun SplashScreen(onGetStartedClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Replace with your app logo
        Image(painter = painterResource(id = R.drawable.hrl), contentDescription = null)
        Text(text = "Welcome to HRL", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onGetStartedClick() }) { // Call the function passed as a parameter
            Text("Get Started")
        }
    }
}

// Login/Register Screen
@Composable
fun LoginRegisterScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) }

    // State for error messages
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (isRegistering) {
            Text("Register", style = MaterialTheme.typography.headlineMedium)
            // First Name
            TextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
            // Last Name
            TextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
            // Date of Birth
            TextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth") })
            // Email
            TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            // Password
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            // Error message for registration
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
            }
            Button(onClick = {
                errorMessage = validateRegistration(firstName, lastName, dob, email, password)
            }) {
                Text("Register")
            }
        } else {
            Text("Login", style = MaterialTheme.typography.headlineMedium)
            // Email
            TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            // Password
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            // Error message for login
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
            }
            Button(onClick = {
                // Handle login logic here, resetting the error message if necessary
            }) {
                Text("Login")
            }
            Button(onClick = {
                isRegistering = true
                errorMessage = "" // Reset error message when switching to register
            }) {
                Text("Go to Register")
            }
        }
    }
}

// Validation Function
fun validateRegistration(firstName: String, lastName: String, dob: String, email: String, password: String): String {
    // Check for empty fields
    if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || email.isEmpty() || password.isEmpty()) {
        return "All fields must be filled."
    }
    // Check for valid email and name length
    if (!email.contains("@")) {
        return "Please enter a valid email."
    }
    if (firstName.length < 3 || firstName.length > 30) {
        return "First name must be between 3 and 30 characters."
    }
    // Registration success
    return ""
}

// Preview
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HrlTheme {
        AppContent() // Change here to reflect the navigation state in preview
    }
}
