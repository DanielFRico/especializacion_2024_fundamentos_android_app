package co.unab.edu.daferile.appfundamentos.users.repository

import co.unab.edu.daferile.appfundamentos.core.data.network.entity.UserEntity
import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import co.unab.edu.daferile.appfundamentos.users.core.data.network.datasource.UsersFirestoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepository @Inject constructor(private val dataSource: UsersFirestoreDataSource){
    fun usersListFirebase(): Flow<List<User>> {
         return dataSource.getAll()
    }

    fun findUserById(email: String): Flow<User> {
        return  dataSource.getUserById(email)
    }
}

fun UserEntity.toUser(): User = User(
    id = this.id,
    name = this.name,
    document = this.document,
    email = this.email,
    image = this.image
)