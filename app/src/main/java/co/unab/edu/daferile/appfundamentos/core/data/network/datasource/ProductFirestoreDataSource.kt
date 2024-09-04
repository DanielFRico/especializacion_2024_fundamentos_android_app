package co.unab.edu.daferile.appfundamentos.core.data.network.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.unab.edu.daferile.appfundamentos.core.data.local.entity.ProductEntity
import co.unab.edu.daferile.appfundamentos.core.data.network.FirebaseClient
import co.unab.edu.daferile.appfundamentos.core.data.repository.toEntity
import co.unab.edu.daferile.appfundamentos.core.data.repository.toProduct
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProductFirestoreDataSource @Inject constructor(private val firebaseClient: FirebaseClient) {
    fun getAll(): Flow<List<Product>> {
        return callbackFlow {
            val query = firebaseClient.firestoreDB.collection("products")
            val subscription = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close()
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val documents =
                        snapshot.documents.map { document -> document.toObject(ProductEntity::class.java) }
                    trySend(documents.map { productEntity -> productEntity!!.toProduct() })
                }
            }
            awaitClose {
                subscription.remove()
            }
        }
    }

    fun getByID(id: Int): LiveData<Product> {
        val product = MutableLiveData<Product>()
        firebaseClient.firestoreDB.collection("products").document(id.toString())
            .addSnapshotListener { snapshot, _ ->
                val productEntity = snapshot!!.toObject(ProductEntity::class.java)
                productEntity?.id = id
                product.postValue(productEntity?.toProduct())
            }
        return product
    }

    fun add(product: Product) {
        firebaseClient.firestoreDB.collection("products").document(product.id.toString())
            .set(product.toEntity())
    }

    fun update(product: Product) {
        val productEntity = product.toEntity().copy(id = product.id)
        firebaseClient.firestoreDB.collection("products").document(product.id.toString())
            .set(productEntity)
    }

    fun remove(product: Product) {
        firebaseClient.firestoreDB.collection("products").document(product.id.toString()).delete()
    }
}