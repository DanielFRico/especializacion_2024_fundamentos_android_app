package co.unab.edu.daferile.appfundamentos.signup.domain

import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import co.unab.edu.daferile.appfundamentos.signup.data.repository.SingupRespository
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val singupRespository: SingupRespository,
    private val createUserFirestoreUseCase: CreateUserFirestoreUseCase
) {

    suspend operator fun invoke(user: User, password: String): Boolean {
        val accountResult = singupRespository.createAccount(user.email, password)
        return if (accountResult !== null) {
            println("Created user con id: ${accountResult.user?.uid}")
            createUserFirestoreUseCase(user.copy(id= accountResult.user?.uid.toString()))
            true
        } else {
            false
        }
    }
}