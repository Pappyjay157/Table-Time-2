package com.example.tabletime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState

class AuthViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val auth_State = MutableLiveData<AuthState>()
    val authstate: LiveData<AuthState> = auth_State


    fun testAuthStatus() {
        if (auth.currentUser == null) {
            auth_State.value = AuthState.Unauthenticated

        }else{
            auth_State.value = AuthState.Authenticated
        }

    }
     fun Signin(email: String, password: String ){
         if(email.isEmpty() || password.isEmpty()){
             auth_State.value = AuthState.Error("Please Enter your email and password")
             return
         }
         auth_State.value = AuthState.loading
         auth.signInWithEmailAndPassword(email, password)
             .addOnCompleteListener{ task ->
                 if(task.isSuccessful){
                     auth_State.value = AuthState.Authenticated
                 }else{
                     auth_State.value = AuthState.Error(task.exception?.message?: "Error!!!! Not correct")
                 }

             }


     }
    fun Signup(email: String, password: String ){
        if(email.isEmpty() || password.isEmpty()){
            auth_State.value = AuthState.Error("Please Enter your email and password")
            return
        }
        auth_State.value = AuthState.loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    auth_State.value = AuthState.Authenticated
                }else{
                    auth_State.value = AuthState.Error(task.exception?.message?: "Error!!!! Not correct")
                }

            }


    }

    fun signout(){
        auth.signOut()
        auth_State.value = AuthState.Unauthenticated
    }




    sealed class AuthState {
        object Authenticated : AuthState()
        object Unauthenticated : AuthState()
        object loading : AuthState()
        data class Error(val message: String) : AuthState()
    }
}