package com.example.assignment.ui.Screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignment.Navigation.Screen
import com.example.assignment.R
import com.example.assignment.utils.AuthResultContract

import com.example.assignment.viewModel.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

private const val TAG = "LoginScreen"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val signInRequestCode = 1
    val context = LocalContext.current
    var isclicked by remember { mutableStateOf(false) }
    val userEmail = loginViewModel.userEmail.value
    val password = loginViewModel.password.value
    val isLoggedin =loginViewModel.isLoggedIn.collectAsState().value
    val callbackManager = CallbackManager.Factory.create()
    val contextFb = LocalContext.current as ActivityResultRegistryOwner

    if(isLoggedin){
        navController.navigate(Screen.NewView.route)
    }

    registerFacebookCallback(callbackManager, loginViewModel,context)


    val authResultLauncher = rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    Toast.makeText(context, "Google Login Failed", Toast.LENGTH_SHORT).show()
                } else {
                    loginViewModel.signInWithGoogleToken(account.idToken!!)
                    coroutineScope.launch {

                        navController.navigate(Screen.NewView.route)
                    }
                }
            } catch (e: ApiException) {
                println(e.message)
            }
        }





    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(top = 12.dp, bottom = 100.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(start = 50.dp, top = 20.dp, end = 50.dp),
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "SignIn into your \n Account",
                color = Color.Red,
                fontFamily = FontFamily.Monospace,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Email", fontWeight = FontWeight.Bold)
                TextField(
                    value = userEmail,
                    onValueChange = {
                        loginViewModel.setUserEmail(it)
                    },
                    placeholder = { Text(text = "johndoe@gmail,com") },
                    trailingIcon = {
                        Icon(Icons.Filled.Email, contentDescription = null, tint = Color.Red)
                    }, modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )

            }

            Spacer(modifier = Modifier.height(40.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Password", fontWeight = FontWeight.Bold)
                TextField(
                    value = password,
                    onValueChange = {
                        loginViewModel.setPassword(it)
                    },
                    placeholder = { Text(text = "Password") },
                    trailingIcon = {
                        Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.Red)
                    }, modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Forgot Password ?",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Login with",
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {


                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            authResultLauncher.launch(signInRequestCode)
                        }
                )
                Spacer(modifier = Modifier.size(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp).
                            clickable{
                                val permissions = listOf("email", "public_profile")
                                LoginManager.getInstance().logIn(contextFb,callbackManager,permissions)
                            }
                )

            }
            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Don't have an Account ? ")
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = "Register Now", color = Color.Red)
            }

        }
        Card(
            Modifier
                .padding(top = 30.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .height(80.dp)
                .fillMaxWidth()
                .align(Alignment.BottomEnd),

            colors = CardDefaults.cardColors(Color.Red)
        )

        {
            Row(
                Modifier
                    .fillMaxSize()
                    .clickable(enabled = loginViewModel.isValidEmailAndPassword(), onClick = {
                        isclicked = !isclicked
                        loginViewModel.signInWithEmailAndPassword()
                    })
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isclicked) "Signing in..." else "Login",
                    fontSize = 32.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(12.dp)
                )
                if (isclicked) {
                    Spacer(modifier = Modifier.width(16.dp))
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(16.dp)
                            .width(16.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                }


            }

        }

    }


}

fun registerFacebookCallback(callbackManager: CallbackManager, viewModel: LoginViewModel,context:Context) {
    LoginManager.getInstance().registerCallback(callbackManager, object :
        FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult) {
            Log.d(TAG, "facebook:onSuccess:$result")
            viewModel.signInWithFacebookToken(result.accessToken)

        }

        override fun onCancel() {
            Log.d(TAG, "facebook:onCancel")
        }
        override fun onError(error: FacebookException) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            Log.d(TAG, "facebook:onError", error)
        }
    })
}

