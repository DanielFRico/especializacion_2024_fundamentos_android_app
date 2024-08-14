package co.unab.edu.daferile.appfundamentos.home.ui

import co.unab.edu.daferile.appfundamentos.core.ui.model.Product

sealed interface ProductListUIState {
    data object Loading : ProductListUIState
    data class Success(val productsList: List<Product>): ProductListUIState
    data class Error(val throwable: Throwable):ProductListUIState
}