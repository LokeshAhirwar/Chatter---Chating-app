package com.example.mychat.modals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel: ViewModel() {

    val auth:FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val db = Firebase.firestore
    var authState: LiveData<AuthState> = _authState

    init {
        checkAuthState()
    }


    fun checkAuthState(){
        if(auth.currentUser==null){
            _authState.value = AuthState.unothenticated
        }else
        {
            _authState.value = AuthState.authenticated
        }
    }

    fun login(email: String,password: String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.failure("Email and password cannot be empty")
        }
        _authState.value = AuthState.loading
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if(task.isSuccessful){
                _authState.value = AuthState.authenticated

            }else{
                _authState.value = AuthState.failure(task.exception?.message?:"Something Went wrong")
            }
        }
    }
    fun singUp(fullName:String,userName: String,mobileNumber: String,about: String,email: String,password: String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.failure("Email and password cannot be empty")
        }
        _authState.value = AuthState.loading
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if(task.isSuccessful){
                val uid = task.result.user?.uid?: return@addOnCompleteListener
                val user = hashMapOf(
                    "user Id"  to uid,
                    "fullName" to fullName,
                    "userName" to userName,
                    "mobileNumber" to mobileNumber,
                    "about" to about,
                    "email" to email,
                    "createdAt" to FieldValue.serverTimestamp()

                )
                db.collection("users").add(user).addOnCompleteListener {
                    if(it.isSuccessful){
                        _authState.value = AuthState.authenticated
                    }else {
                        _authState.value =
                            AuthState.failure(it.exception?.message ?: "Something Went wrong")
                    }
                }
            }else{
                _authState.value = AuthState.failure(task.exception?.message?:"Something Went wrong")
            }
        }
    }

    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.unothenticated
    }

    private var _dob = MutableStateFlow("")
    val dob = _dob.asStateFlow()
    private var _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private var _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()
    private var _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility = _passwordVisibility.asStateFlow()



    fun changeName(name: String){
        _name.value = name
    }
    fun changeEmail(email: String){
        print(email)
        _email.value = email
    }
    fun changePassword(password: String){
        _password.value = password
    }
    fun changeConfirmPassword(confirmPassword: String){
        _confirmPassword.value = confirmPassword

    }
    fun changePswVisibility(pswVisibility: Boolean){
        _passwordVisibility.value = pswVisibility

    }

    fun changeDOB(date:String){
        _dob.value = date

    }

}

sealed class AuthState{
    object authenticated: AuthState()
    object unothenticated: AuthState()
    object loading: AuthState()
    data class failure(val message:String): AuthState()
}