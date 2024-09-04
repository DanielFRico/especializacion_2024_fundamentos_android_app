package co.unab.edu.daferile.appfundamentos.home.domain

import co.unab.edu.daferile.appfundamentos.core.data.repository.HomeRepository
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsListUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    operator fun invoke(): Flow<List<Product>> {
        //return homeRepository.productList
        return homeRepository.productsListFirebase()
    }
}