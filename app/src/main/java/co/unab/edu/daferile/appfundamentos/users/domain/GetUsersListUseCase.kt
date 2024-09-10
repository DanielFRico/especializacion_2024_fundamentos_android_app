package co.unab.edu.daferile.appfundamentos.users.domain

import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import co.unab.edu.daferile.appfundamentos.users.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    operator fun invoke(): Flow<List<User>> {
        return usersRepository.usersListFirebase()
    }
}