package com.example.tabletime

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tabletime.Screens.HomeScreen
import com.example.tabletime.Screens.SigninScreen
import com.example.tabletime.Screens.SignupScreen

@Composable
fun Navigation(modifier: Modifier, authViewModel: AuthViewModel){
    val navcontroller = rememberNavController()

    NavHost(navController = navcontroller, startDestination = "Signup", builder ={
        composable("Signin"){
            SigninScreen(modifier, navcontroller , authViewModel)
        }
        composable("Signup"){
            SignupScreen(modifier,navcontroller, authViewModel)
        }
        composable("Home"){
            HomeScreen(modifier,navcontroller, authViewModel)
        }

    } )
}