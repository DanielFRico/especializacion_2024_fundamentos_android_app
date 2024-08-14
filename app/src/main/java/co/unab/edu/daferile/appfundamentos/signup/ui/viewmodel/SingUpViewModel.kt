package co.unab.edu.daferile.appfundamentos.signup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import co.unab.edu.daferile.appfundamentos.signup.domain.CreateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(private val createAccountUseCase: CreateAccountUseCase) :
    ViewModel() {
    private val _onCreateAccount = MutableStateFlow<Boolean>(false)
    val onCreateAccount: StateFlow<Boolean> = _onCreateAccount
    fun createAccount(
        name: String,
        document: String,
        password: String,
        email: String,
        image: String
    ) {
        val user = User(name = name, document = document.toLong(), email = email, image = image)
        viewModelScope.launch(Dispatchers.IO) {
            _onCreateAccount.emit(createAccountUseCase(user, password))
        }
    }
}