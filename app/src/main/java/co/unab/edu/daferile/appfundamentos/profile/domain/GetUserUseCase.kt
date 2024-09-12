package co.unab.edu.daferile.appfundamentos.profile.domain

import co.unab.edu.daferile.appfundamentos.users.repository.UsersRepository
import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    operator fun invoke(email: String): Flow<User> {
        return usersRepository.findUserById(email)
    }
}