package com.example.mychat.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mychat.modals.AuthViewModel

@Composable
fun chatScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navcontroller: NavHostController
) {
    Text("this is chat screen")

}