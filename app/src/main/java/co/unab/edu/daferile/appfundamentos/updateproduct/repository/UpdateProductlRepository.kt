package co.unab.edu.daferile.appfundamentos.updateproduct.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import co.unab.edu.daferile.appfundamentos.core.data.local.dao.ProductDao
import co.unab.edu.daferile.appfundamentos.core.data.repository.toProduct
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import javax.inject.Inject

class UpdateProductlRepository @Inject constructor(private val productDao: ProductDao) {
    fun getProductById(id: Int): LiveData<Product> {
        return productDao.getById(id).map { product -> product.toProduct() }
    }

    fun updateProduct(product: Product) {

    }
}