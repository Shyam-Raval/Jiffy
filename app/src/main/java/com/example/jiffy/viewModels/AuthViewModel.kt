package com.example.jiffy.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jiffy.screens.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val  auth : FirebaseAuth
): ViewModel() {
    sealed class AuthState{
        object Idle : AuthState()
        object Loading:AuthState()
        data class Success(val user : String ): AuthState()
        data class Error(val msg : String): AuthState()
    }
  private val _authState  = MutableStateFlow<AuthState>(
      if(auth.currentUser!=null) AuthState.Success(auth.currentUser!!.uid)
      else AuthState.Idle
  )

    val authState: StateFlow<AuthState> = _authState

    val isLoggedIn: Boolean
        get() = authState.value is AuthState.Success

    //getting current user
    val currentUser = auth.currentUser?.let{
        firebaseUser -> UserProfile(
            uid = firebaseUser.uid,
            name = firebaseUser.displayName?:"User",
            email = firebaseUser.email?: ""
        )
    }

    //login

    fun login(email : String , password : String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(
                    email, password
                ).await()
                result.user?.let {
                    _authState.value = AuthState.Success(it.uid)


                } ?: run {
                    _authState.value = AuthState.Error("Login Failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown Error")
            }
        }

    }

    fun signUp(name:String , email : String  , password: String){
        viewModelScope.launch {
            try{
                _authState.value = AuthState.Loading
                auth.createUserWithEmailAndPassword(email , password).await()
                _authState.value  =AuthState.Success("SignUp Successful")
            }catch (e:Exception){
                _authState.value = AuthState.Error(e.message?:"SignUp Failed!!!")
            }
        }
    }

    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.Idle
    }


}