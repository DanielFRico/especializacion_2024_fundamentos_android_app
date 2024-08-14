package co.unab.edu.daferile.appfundamentos.updateproduct.domain

import co.unab.edu.daferile.appfundamentos.core.data.repository.HomeRepository
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.updateproduct.repository.UpdateProductlRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val updateProductlRepository: UpdateProductlRepository) {
    operator fun invoke(product: Product) {
        updateProductlRepository.updateProduct(product = product)
    }
}