package com.example.assignment.ui.SearchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.assignment.Navigation.Screen
import com.example.assignment.Repo.State
import com.example.assignment.ui.NewsScreen.ListOfContent
import com.example.assignment.viewModel.SearchViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SearchScreen(

    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val searchQuery by searchViewModel.searchQuery
    val state = searchViewModel.searchedState.collectAsState().value
    Scaffold(
        topBar = {
            SearchWidget(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                },
                onSearchClicked = {
                    searchViewModel.searchNews(query = it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = {
            when (state) {
                is State.Empty -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Empty")
                    }
                }

                is State.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is State.Loaded -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        ListOfContent( state.list)
                        Button(
                            onClick = {
                                Firebase.auth.signOut()
                                navController.navigate(Screen.AuthScreen.route)
                            },
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .width(120.dp)
                                .height(60.dp)
                                .align(Alignment.BottomCenter),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White),
                            shape = RoundedCornerShape(24.dp)

                        ) {
                            androidx.compose.material3.Text(text = "Sign out",  fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterVertically))
                        }
                    }
                }

                is State.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.error)
                    }
                }

            }
        }
    )
}