package com.example.tabletime.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tabletime.AuthViewModel
import com.example.tabletime.R
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // Drawer items list
    val drawerItems = listOf(
        DrawerItems(Icons.Default.Person, "User Profile", 0, false),
        DrawerItems(Icons.Default.Favorite, "Restaurants", 0, true),
        DrawerItems(Icons.Default.Home, "Home", 0, false),
        DrawerItems(Icons.Default.ExitToApp, "Sign out", 0, hasBadge = false)
    )

    // State variables
    var selectedItems by remember { mutableStateOf(drawerItems[0]) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // State for the user profile details
    var userName by remember { mutableStateOf("") }
    val userEmail = authViewModel.getCurrentUserEmail() ?: "Unknown Email"

    // Modal drawer with items
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE3F2FD)) // Light blue drawer background
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color(0xFF2196F3)), // Blue background for the header section
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            Modifier.wrapContentSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.image),
                                contentDescription = "Booking System",
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = "Find Your Next Favorite Meal Spot",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White // White text for contrast
                            )
                        }
                    }

                    // Drawer menu items
                    drawerItems.forEach {
                        NavigationDrawerItem(
                            label = { Text(text = it.text) },
                            selected = it == selectedItems,
                            onClick = {
                                selectedItems = it
                                scope.launch {
                                    drawerState.close()
                                }

                                // Handle specific drawer item actions
                                when (it.text) {
                                    "User Profile" -> navController.navigate("UserProfileScreen")
                                    "Sign out" -> {
                                        authViewModel.signout()
                                        navController.navigate("Signin")
                                    }
                                }
                            },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            icon = {
                                Icon(imageVector = it.icon, contentDescription = it.text)
                            },
                            badge = {
                                if (it.hasBadge) {
                                    Badge { Text(text = it.badgeCount.toString(), fontSize = 12.sp) }
                                }
                            }
                        )
                    }
                }
            }
        },
    ) {
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
