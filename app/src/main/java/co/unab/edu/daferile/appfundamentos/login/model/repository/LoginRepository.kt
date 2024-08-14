package co.unab.edu.daferile.appfundamentos.login.model.repository

import co.unab.edu.daferile.appfundamentos.core.data.network.FirebaseClient
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(private val firebaseClient: FirebaseClient) {
    suspend fun login(email: String, password: String): AuthResult? {
        return kotlin.runCatching {
            firebaseClient.auth.signInWithEmailAndPassword(email, password).await()
        }.getOrNull()
    }
}