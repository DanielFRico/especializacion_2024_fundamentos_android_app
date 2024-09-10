package co.unab.edu.daferile.appfundamentos.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.home.domain.AddProductUseCase
import co.unab.edu.daferile.appfundamentos.home.domain.GetProductsListUseCase
import co.unab.edu.daferile.appfundamentos.home.domain.RemoveProductUseCase
import co.unab.edu.daferile.appfundamentos.home.ui.ProductListUIState
import co.unab.edu.daferile.appfundamentos.home.ui.ProductListUIState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getProductsListUseCase: GetProductsListUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val removeProductUseCase: RemoveProductUseCase
) :
    ViewModel() {

    val uiState: StateFlow<ProductListUIState> =
        getProductsListUseCase().map(::Success).catch { error ->
            ProductListUIState.Error(error)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListUIState.Loading)
    /*
        fun loadProducts() {
            if (productList.value == null) {
                viewModelScope.launch(Dispatchers.IO) {
                    addProductUseCase(
                        Product(
                            1,
                            "my first produc",
                            20000,
                            "delicious",
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMm4n0xDPUo-MkJrZ_5VNhR-bxcnYCPLTbOA&s"
                        )
                    )
                }

            }
        }
    */
    fun removeProduct(productToRemove: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            removeProductUseCase(productToRemove)
        }
    }
}