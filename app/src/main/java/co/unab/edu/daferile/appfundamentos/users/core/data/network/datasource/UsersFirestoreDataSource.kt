package co.unab.edu.daferile.appfundamentos.users.core.data.network.datasource

import co.unab.edu.daferile.appfundamentos.core.data.network.FirebaseClient
import co.unab.edu.daferile.appfundamentos.core.data.network.entity.UserEntity
import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import co.unab.edu.daferile.appfundamentos.users.repository.toUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UsersFirestoreDataSource @Inject constructor(private val firebaseClient: FirebaseClient){
    fun getAll(): Flow<List<User>> {
        return callbackFlow {
            val query = firebaseClient.firestoreDB.collection("users")
            val subscription = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close()
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val documents =
                        snapshot.documents.map { document -> document.toObject(UserEntity::class.java) }
                    trySend(documents.map { userEntity -> userEntity!!.toUser() })
                }
            }
            awaitClose {
                subscription.remove()
            }
        }
    }
}