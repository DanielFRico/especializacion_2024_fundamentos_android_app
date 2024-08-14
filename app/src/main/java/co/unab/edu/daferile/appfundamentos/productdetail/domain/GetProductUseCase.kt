package co.unab.edu.daferile.appfundamentos.productdetail.domain

import androidx.lifecycle.LiveData
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.productdetail.data.repository.ProductDetailRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val productDetailRepository: ProductDetailRepository) {

    operator fun invoke(id: Int): LiveData<Product> = productDetailRepository.getProductById(id)
}