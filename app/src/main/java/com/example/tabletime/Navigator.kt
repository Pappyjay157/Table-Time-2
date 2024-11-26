import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tabletime.AuthViewModel
import com.example.tabletime.R
import com.example.tabletime.Screens.HomeScreen
import com.example.tabletime.Screens.SigninScreen
import com.example.tabletime.Screens.SignupScreen
import com.example.tabletime.Screens.UserProfileScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(modifier: Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    var currentDestination by remember { mutableStateOf("Signup") }

    val routesWithMenu = listOf("Home", "UserProfileScreen")

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentDestination = destination.route!!
        }
    }

    // Drawer state and coroutine scope
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Drawer items
    val drawerItems = listOf(
        DrawerItems(Icons.Default.Person, "User Profile", 0, false),
        DrawerItems(Icons.Default.Favorite, "Restaurants", 0, true),
        DrawerItems(Icons.Default.Home, "Home", 0, false),
        DrawerItems(Icons.Default.ExitToApp, "Sign out", 0, hasBadge = false)
    )
    var selectedItems by remember { mutableStateOf(drawerItems[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE3F2FD))
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color(0xFF2196F3)),
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
                                color = Color.White
                            )
                        }
                    }

                    drawerItems.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(text = item.text) },
                            selected = item == selectedItems,
                            onClick = {
                                selectedItems = item
                                scope.launch { drawerState.close() }

                                when (item.text) {
                                    "User Profile" -> navController.navigate("UserProfileScreen")
                                    "Sign out" -> {
                                        authViewModel.signout()
                                        navController.navigate("Signin")
                                    }
                                }
                            },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            icon = { Icon(imageVector = item.icon, contentDescription = item.text) },
                            badge = {
                                if (item.hasBadge) {
                                    Badge { Text(text = item.badgeCount.toString(), fontSize = 12.sp) }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentDestination in routesWithMenu) {
                    TransparentTopBar(
                        onMenuClick = {
                            scope.launch { drawerState.open() } // Open the drawer
                        }
                    )
                }
            },
            content = { padding ->
                NavHost(
                    modifier = modifier.padding(padding),
                    navController = navController,
                    startDestination = "Signup",
                    builder = {
                        composable("Signin") { SigninScreen(modifier, navController, authViewModel) }
                        composable("Signup") { SignupScreen(modifier, navController, authViewModel) }
                        composable("Home") { HomeScreen(modifier, navController, authViewModel) }
                        composable("UserProfileScreen") { UserProfileScreen(modifier, navController, authViewModel) }
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.height(50.dp),
        title = { Text(text = "MENU", color = Color.Black) },
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
            }
        },
    )
}

data class DrawerItems(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val text: String,
    val badgeCount: Int,
    val hasBadge: Boolean
)
