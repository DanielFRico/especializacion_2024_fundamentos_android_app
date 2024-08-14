package co.unab.edu.daferile.appfundamentos.login.domain

import co.unab.edu.daferile.appfundamentos.login.model.repository.LoginRepository
import co.unab.edu.daferile.appfundamentos.login.viewmodel.LoginUIState
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String): LoginUIState {
        val loginResult = loginRepository.login(email, password)
        return if (loginResult != null) {
            LoginUIState.Success
        } else {
            LoginUIState.Error
        }
    }
}