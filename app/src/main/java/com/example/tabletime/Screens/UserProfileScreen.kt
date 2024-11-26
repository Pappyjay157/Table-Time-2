package com.example.tabletime.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tabletime.AuthViewModel
import com.example.tabletime.R
import com.example.tabletime.WindowType
import com.example.tabletime.rememberWindowsSize
import kotlinx.coroutines.launch

@Composable
fun ProfileLandscape(modifier: Modifier, authViewModel: AuthViewModel) {
    val email = authViewModel.getCurrentUserEmail() ?: "Unavailable"
    var name by remember { mutableStateOf("Ogundeko Ayooluwa") }
    var phone by remember { mutableStateOf("07475629420") }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // Light blue background
            .padding(16.dp)
    ) {
        // Profile Picture Section
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        }

        // Editable Profile Data Section
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp)
        ) {
            EditableField(label = "Name", value = name, onValueChange = { name = it })
            EditableField(label = "Phone", value = phone, onValueChange = { phone = it })
            DisplayField(label = "Email", value = email)

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    // Save logic here
                    // Example: authViewModel.updateUserProfile(name, phone)
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)) // Darker blue for button
            ) {
                Text(
                    text = "Save",
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun EditableField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray,
                disabledTextColor = Color.Gray.copy(alpha = 0.5f),
                errorTextColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.LightGray,
                focusedIndicatorColor = Color(0xFF1976D2), // Blue for focused border
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.LightGray,
                cursorColor = Color(0xFF1976D2) // Blue for the cursor
            )
        )
    }
}

@Composable
fun DisplayField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(
            text = value,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            color = Color.DarkGray
        )
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val email = authViewModel.getCurrentUserEmail() ?: "Unavailable"
    var name by remember { mutableStateOf("Ogundeko Ayooluwa") }
    var phone by remember { mutableStateOf("07475629420") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // Light blue background
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Profile Picture Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(0xFF2196F3)), // Blue background
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Editable Profile Data Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            EditableField(label = "Name", value = name, onValueChange = { name = it })
            EditableField(label = "Phone", value = phone, onValueChange = { phone = it })
            DisplayField(label = "Email", value = email)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button
        Button(
            onClick = {
                navController.navigate("Home")
            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .align(Alignment.CenterHorizontally)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)) // Darker blue for button
        ) {
            Text(
                text = "Save",
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val windowSize = rememberWindowsSize()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                val drawerItems = listOf(
                    DrawerItems(Icons.Default.Person, "User Profile", 0, false),
                    DrawerItems(Icons.Default.Favorite, "Restaurants", 0, true),
                    DrawerItems(Icons.Default.Home, "Home", 0, false),
                    DrawerItems(Icons.Default.ExitToApp, "Sign out", 0, hasBadge = false)
                )

                // Drawer Items with Navigation Logic
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.text) },
                        selected = false,
                        onClick = {
                            // Handle item click
                            when (item.text) {
                                "Sign out" -> {
                                    authViewModel.signout()
                                    navController.navigate("Signin")
                                }
                                "Home" -> navController.navigate("Home")
                                "User Profile" -> navController.navigate("UserProfile")
                            }
                            scope.launch {
                                drawerState.close() // Close the drawer after selection
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.text) }
                    )
                }
            }
        }
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            // "MENU" Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .background(Color(0xFF1976D2)), // Darker blue for the button
                ) {
                    Text(
                        text = "MENU",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
                // Profile Content Based on Window Size
                when (windowSize.width) {
                    WindowType.SMALL -> ProfileScreen(modifier, authViewModel, navController)
                    else -> ProfileLandscape(modifier, authViewModel)
                }
            }
        }
    }
// DrawerItems data class
data class DrawerItems(
    val icon: ImageVector,
    val text: String,
    val badgeCount: Int,
    val hasBadge: Boolean
)


