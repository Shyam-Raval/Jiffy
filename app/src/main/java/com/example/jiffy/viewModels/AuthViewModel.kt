package com.example.jiffy.viewModels

import androidx.lifecycle.ViewModel
import com.example.jiffy.screens.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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


}