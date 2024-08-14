package co.unab.edu.daferile.appfundamentos.signup.data.repository

import co.unab.edu.daferile.appfundamentos.core.data.network.FirebaseClient
import co.unab.edu.daferile.appfundamentos.core.data.network.entity.UserEntity
import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SingupRespository @Inject constructor(private val client: FirebaseClient) {
    suspend fun createAccount(email: String, password: String): AuthResult? {
        return client.auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun createUserFirestore(user: User): Boolean {
        return kotlin.runCatching {
            client.firestoreDB.collection("users").document(user.id).set(user.toEntity()).await()
        }.isSuccess
    }
}

fun User.toEntity(): UserEntity = UserEntity(this.id, this.name, this.document, this.email, this.image)