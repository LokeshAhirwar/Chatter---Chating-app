package com.example.mychat

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mychat.modals.AuthViewModel
import com.example.mychat.views.homeScreen
import com.example.mychat.views.loginScreen
import com.example.mychat.views.signUpScreen
import kotlinx.serialization.Serializable





@Serializable
sealed class Routes{
    @Serializable
    object Login: Routes()
    @Serializable
    object Home: Routes()
    @Serializable
    object SignUp: Routes()

}
//data object login
//data object signup
//data object home

@Composable
fun appNavigation(modifier: Modifier = Modifier,viewModel: AuthViewModel) {
    val navcontroller = rememberNavController()
    NavHost(navController = navcontroller, startDestination = Routes.Login){
        composable<Routes.Login> {
            loginScreen(modifier,viewModel,navcontroller)
        }
        composable<Routes.Home> {
            homeScreen(modifier,viewModel,navcontroller)
        }
        composable<Routes.SignUp> {
            signUpScreen(modifier,viewModel,navcontroller)
        }

    }


}