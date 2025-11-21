package com.example.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.presentation.data.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern

class LoginViewModel: ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState(
        email = "",
        password = "",
        isEmailValid = false,
        isPasswordValid = false,
        isActive = false
    ))

    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

    fun onEmailChanged(email:String){
        val isEmailVerify = checkEmail(email)

        _loginState.update { login ->
            login.copy(
                email = email,
                isEmailValid = isEmailVerify,
                isActive = isEmailVerify && login.isPasswordValid
            )
        }

    }
    fun onPasswordChanged(password:String) {
        val isPasswordVerify = password.isNotBlank()
        _loginState.update { login ->
            login.copy(
                password    = password,
                isPasswordValid =isPasswordVerify,
                isActive = isPasswordVerify && login.isEmailValid
            )
        }
    }
    fun checkEmail(email: String): Boolean{
        return  emailPattern.matcher(email).matches()
    }

}