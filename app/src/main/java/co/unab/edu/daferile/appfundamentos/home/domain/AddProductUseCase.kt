package co.unab.edu.daferile.appfundamentos.home.domain

import co.unab.edu.daferile.appfundamentos.core.data.repository.HomeRepository
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(product: Product) {
        homeRepository.addProductFirebase(product = product)
    }
}