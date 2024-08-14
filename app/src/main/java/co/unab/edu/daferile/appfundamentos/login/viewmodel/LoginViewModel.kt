package co.unab.edu.daferile.appfundamentos.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.unab.edu.daferile.appfundamentos.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Default)
    val uiState: StateFlow<LoginUIState> = _uiState
    fun checkIsValidLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(
                loginUseCase(
                    email = email.value!!,
                    password = password.value!!
                )
            )
        }
    }

    fun onLoginChange(emailValue: String, passwordValue: String) {
        _email.value = emailValue
        _password.value = passwordValue
    }
}