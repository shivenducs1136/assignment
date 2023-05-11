package com.example.assignment.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.Application
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
   @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn  = _isLoggedIn.asStateFlow()


    private val _userEmail = mutableStateOf("")
    val userEmail: State<String> = _userEmail

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    // Setters
    fun setUserEmail(email: String) {
        _userEmail.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }


    init {
        _isLoggedIn.value = getCurrentUser() != null
    }

    fun createUserWithEmailAndPassword() = viewModelScope.launch {

        Firebase.auth.createUserWithEmailAndPassword(userEmail.value, password.value)
            .addOnCompleteListener { task -> signInCompletedTask(task) }
    }

    fun signInWithEmailAndPassword() = viewModelScope.launch {
        try {

            Firebase.auth.signInWithEmailAndPassword(userEmail.value, password.value)
                .addOnCompleteListener { task -> signInCompletedTask(task) }
        } catch (e: Exception) {
            Toast.makeText(
                appContext,
                e.localizedMessage ?: "Unknown error",
                Toast.LENGTH_SHORT
            ).show()
            Log.d(TAG, "Sign in fail: $e")
        }
    }

    fun signInWithGoogleToken(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        signWithCredential(credential)
    }

    fun signInWithFacebookToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        signWithCredential(credential)
    }

    private fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {

            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task -> signInCompletedTask(task) }
        } catch (e: Exception) {
            Toast.makeText(
                appContext,
                e.localizedMessage ?: "Unknown error",
                Toast.LENGTH_SHORT
            ).show()

        }
    }


    private fun signInCompletedTask(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Toast.makeText(appContext, "Login Successful", Toast.LENGTH_SHORT)
                .show()
            Log.d(TAG, "SignInWithEmail:success")
        } else {
            Toast.makeText(
                appContext,
                task.exception?.localizedMessage ?: "Unknown error",
                Toast.LENGTH_SHORT
            ).show()


            Log.w(TAG, "SignInWithEmail:failure", task.exception)
        }
        viewModelScope.launch {
            _isLoggedIn.value = getCurrentUser() != null
        }
    }


    private fun getCurrentUser(): FirebaseUser? {
        val user = Firebase.auth.currentUser
        Log.d(TAG, "user display name: ${user?.displayName}, email: ${user?.email}")
        return user
    }

    fun isValidEmailAndPassword(): Boolean {
        if (userEmail.value.isBlank() || password.value.isBlank()) {
            return false
        }
        return true
    }

    fun signOut() = viewModelScope.launch {
        Firebase.auth.signOut()
        _isLoggedIn.value = false
    }


}
