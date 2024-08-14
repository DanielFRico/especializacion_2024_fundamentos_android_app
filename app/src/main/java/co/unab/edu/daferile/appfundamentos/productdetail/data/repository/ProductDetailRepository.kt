package co.unab.edu.daferile.appfundamentos.productdetail.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import co.unab.edu.daferile.appfundamentos.core.data.local.dao.ProductDao
import co.unab.edu.daferile.appfundamentos.core.data.repository.toProduct
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import javax.inject.Inject

class ProductDetailRepository @Inject constructor(private val productDao: ProductDao) {
    fun getProductById(id: Int): LiveData<Product> {
        return productDao.getById(id).map { product -> product.toProduct() }
    }
}