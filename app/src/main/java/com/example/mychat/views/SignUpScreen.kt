package com.example.mychat.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mychat.R
import com.example.mychat.Routes
import com.example.mychat.modals.AuthState
import com.example.mychat.modals.AuthViewModel


@Composable
fun signUpScreen(modifier: Modifier, viewModel: AuthViewModel,navController: NavController) {
//    val email by viewModel.email.collectAsStateWithLifecycle()
    //    val password by viewModel.password.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordVisibility by viewModel.passwordVisibility.collectAsStateWithLifecycle()
    val authState = viewModel.authState.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.authenticated -> navController.navigate(Routes.Home)
            is AuthState.failure->{
                Toast.makeText(context,(authState.value as AuthState.failure).message, Toast.LENGTH_SHORT).show()


            }
            else -> Unit
        }
    }
    Column(
        modifier = modifier
    ) {
        Text(
            "SignUp",
            modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Welcome",
            modifier = Modifier
                .padding(start = 26.dp),
            fontSize = 30.sp,
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Email",
            modifier = Modifier
                .padding(start = 26.dp),
            fontSize = 24.sp,
            color = Color.Black,
        )
        val focusManager = LocalFocusManager.current
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 12.dp),
            placeholder = {Text("example@gmail.com",)},
            singleLine = true,
            textStyle = TextStyle.Default.copy(fontSize = 20.sp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(true) }),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,

            )

        )

        Text(
            text = "Password",
            modifier = Modifier
                .padding(start = 26.dp, top = 24.dp),
            fontSize = 24.sp,
            color = Color.Black,
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 12.dp),
            singleLine = true,
            textStyle = TextStyle.Default.copy(fontSize = 20.sp),
            trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.changePswVisibility(!passwordVisibility)
                    }
                ) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Star else Icons.Default.Delete,
                        contentDescription = "Password Visibility Button"
                    )
                }
            },
            placeholder = {Text("***********")},
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(true) }),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,

            )
        )
        TextButton(
            onClick = {
                //to do forget password
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 22.dp, top = 4.dp)
        ) {
            Text(
                text = "Forgot Password",
                fontSize = 16.sp,
            )
        }
        Button(
            onClick = {
                viewModel.singUp(email,password)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp)
                .height(55.dp)
                .width(220.dp),
            colors = ButtonDefaults.buttonColors(
            )
        ) {
            Text(
                text = "Sign Up",
                fontSize = 24.sp,
                color = Color.White
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account",
            )
            TextButton(
                onClick = {
                    navController.navigate(Routes.Login)
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Login ",
                )
            }
        }

    }

}