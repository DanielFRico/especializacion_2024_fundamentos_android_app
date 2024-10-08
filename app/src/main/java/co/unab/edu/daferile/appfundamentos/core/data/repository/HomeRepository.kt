package co.unab.edu.daferile.appfundamentos.core.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.core.data.local.dao.ProductDao
import co.unab.edu.daferile.appfundamentos.core.data.local.entity.ProductEntity
import co.unab.edu.daferile.appfundamentos.core.data.network.FirebaseClient
import co.unab.edu.daferile.appfundamentos.core.data.network.datasource.ProductFirestoreDataSource
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val productDAO: ProductDao,
    private val dataSource: ProductFirestoreDataSource
) {
    suspend fun addProduct(product: Product) {
        productDAO.add(product.toEntity())
    }

    suspend fun removeProduct(productToRemove: Product) {
        val toDelete = productToRemove.toEntity()
        toDelete.id = productToRemove.id
        productDAO.delete(toDelete)
    }


    val productList: Flow<List<Product>> = productDAO.getAll().map { items ->
        items.map {
            it.toProduct()
        }
    }

    fun productsListFirebase(): Flow<List<Product>> {
        return dataSource.getAll()
    }

    fun addProductFirebase(product: Product) {
        dataSource.add(product)
    }

    /*
        fun addProductFirebase(product: Product) {
            val productEntity = product.toEntity()
            client.firestoreDB.collection("products").document(productEntity.id.toString())
                .set(productEntity)
        }
     */
}

fun ProductEntity.toProduct(): Product = Product(
    id = this.id,
    name = this.name,
    price = this.price,
    description = this.description,
    image = this.image
)

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = this.id,
    name = this.name,
    price = this.price,
    description = this.description,
    image = this.image
)