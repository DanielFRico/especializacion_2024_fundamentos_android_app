package co.unab.edu.daferile.appfundamentos.productdetail.domain

import androidx.lifecycle.LiveData
import co.unab.edu.daferile.appfundamentos.core.data.network.datasource.ProductFirestoreDataSource
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.productdetail.data.repository.ProductDetailRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productDetailRepository: ProductDetailRepository,
    private val productFirestoreDataSource: ProductFirestoreDataSource
) {

    operator fun invoke(id: Int): LiveData<Product> {
        //return productDetailRepository.getProductById(id)
        return productFirestoreDataSource.getByID(id)
    }
}