package com.example.mychat.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mychat.Routes
import com.example.mychat.modals.AuthState
import com.example.mychat.modals.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homeScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navcontroller: NavHostController
) {
    val authState = viewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.unothenticated -> navcontroller.navigate(Routes.Login)
            else -> Unit
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("My Chat App")
            }, navigationIcon = {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
            },
                actions = {
                    IconButton(onClick = {
                        viewModel.signOut()
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                })
        }
    ) {
        innerpadding->
        Column(modifier = Modifier.fillMaxSize().padding(innerpadding),
            verticalArrangement = Arrangement.Center) {





        }

    }



}