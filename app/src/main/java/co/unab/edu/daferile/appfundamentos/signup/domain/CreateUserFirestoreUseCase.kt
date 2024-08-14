package co.unab.edu.daferile.appfundamentos.signup.domain

import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import co.unab.edu.daferile.appfundamentos.signup.data.repository.SingupRespository
import javax.inject.Inject

class CreateUserFirestoreUseCase @Inject constructor(private val singupRespository: SingupRespository) {

    suspend operator fun invoke(user: User): Boolean {
        return singupRespository.createUserFirestore(user)
    }

}