package com.example.assignment.ui.Screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.assignment.Navigation.Screen
import com.example.assignment.viewModel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SignUpScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    val countries = listOf("+91", "+1")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(countries[0]) }
    var isclicked by remember { mutableStateOf(false) }
    val isLoggedin =loginViewModel.isLoggedIn.collectAsState().value

    if(isLoggedin){
        navController.navigate(Screen.NewView.route)
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
                text = "Create An \n Account",
                color = Color.Red,
                fontFamily = FontFamily.Monospace,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            val email by loginViewModel.userEmail
            var name by remember { mutableStateOf("") }
            var phoneNumber by  remember {
                mutableStateOf("")
            }
            val password by  loginViewModel.password
            Spacer(modifier = Modifier.height(40.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Name", fontWeight = FontWeight.Bold)
                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    placeholder = { Text(text = "John Doe") },
                    trailingIcon = {
                        Icon(Icons.Filled.Person, contentDescription = null, tint = Color.Red)
                    }, modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    )
                )

            }

            Spacer(modifier = Modifier.height(30.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Email", fontWeight = FontWeight.Bold)
                TextField(
                    value = email,
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
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Forgot Password ?",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(text = "Contact  No", fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownDemo()
                    TextField(
                        value = phoneNumber,
                        onValueChange = {
                            phoneNumber = it
                        },
                        placeholder = { Text(text = "9834672813") },
                        trailingIcon = {
                            Icon(Icons.Outlined.Phone, contentDescription = null, tint = Color.Red)
                        }, modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Password", fontWeight = FontWeight.Bold)
                    TextField(
                        value = password,
                        onValueChange = {
                            loginViewModel.setPassword(it)
                        },
                        placeholder = { Text(text = "***********") },
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
                CheckboxComponent()
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Already  have an Account ? ")
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(text = "Sign In !", color = Color.Red)
                }
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
                        loginViewModel.createUserWithEmailAndPassword()
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
                    text = if (isclicked) "Creating Account..." else "Create Account",
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


@Composable
fun CheckboxComponent() {

    val checkedState = remember { mutableStateOf(true) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(

            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "I agree with ")
            Text(
                text = " terms and conditions ",
                color = Color.Red,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }

    }
}

