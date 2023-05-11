package com.example.assignment.Navigation

 sealed class Screen(val route:String){
     object NewView: Screen("NewsScreen")
     object SearchNews: Screen("SearchNews")
     object AuthScreen:Screen("AuthScreen")
 }