package com.example.assignment.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment.ui.Screens.LoginScreen
import com.example.assignment.ui.Screens.SignUpScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi

@Composable
fun AuthScreen(
    navController: NavController,
) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .background(Color.Red),
        topBar = { topAppbar() }

    ) {
        TabLayout(it,navController)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppbar() {
    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(Color.Red),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Social",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "X",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
    )

}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabLayout(paddingValues: PaddingValues, navController: NavController) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column(Modifier.background(Color.Gray)) {
        Tabs(pagerState = pagerState, paddingValues)
        TabsContent(pagerState = pagerState,navController)
    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState, paddingValues: PaddingValues) {
    val list = listOf(
        "Login",
        "Sign Up"
    )
    val scope = rememberCoroutineScope()
    TabRow(
        modifier = Modifier
            .padding(paddingValues)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .height(60.dp),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            Box {}
        }
    ) {
        list.forEachIndexed { index, _ ->
            val selected = pagerState.currentPage == index
           
            Tab(
                modifier = if (selected) Modifier
                    .clip(RoundedCornerShape(bottomEnd = 24.dp))
                    .background(
                        Color.Red
                    )
                else Modifier
                    .clip(RoundedCornerShape(bottomEnd = 24.dp))
                    .background(
                        Color.White
                    ),
                text = {
                    Text(
                        list[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.Gray
                      )

                },
                selected = pagerState.currentPage == index,
                onClick = {

                }
                )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, navController: NavController) {
    HorizontalPager(count = 2, state = pagerState) {

            page ->
        when (page) {
            0 -> LoginScreen(navController = navController)
           1 -> SignUpScreen(navController=navController)
        }
    }
}

