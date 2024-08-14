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

    fun loadFakeProductsList() {
        val productList =
            listOf(
                Product(
                    1,
                    "mani",
                    3000,
                    description = "rico y nutritivo",
                    image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSur3uFIRS6aMWm_Bt8mbCrK4GUAqcYSnnnug&s"
                ),
                Product(
                    2,
                    "salpicon",
                    4500,
                    description = "rico y rojo",
                    image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKU1-3IbXNBzJCwwzQ4EyfdIOYWkBFPaVPaA&s"
                ),
                Product(
                    3,
                    "cocosette",
                    2500,
                    description = "sabe a coco",
                    image = "https://static.merqueo.com/images/products/large/f65078b6-bbcc-4fe6-81ba-113c0ddca32b.jpg"
                ),
                Product(
                    4,
                    "Trump",
                    price = 1000,
                    description = "casi se muere",
                    image = "https://chequeado.com/wp-content/uploads/2024/07/Trump-2.webp"
                ),
                Product(
                    5,
                    "Petroski",
                    price = 500,
                    description = "mamerto",
                    image = "https://preview.redd.it/me-encantan-los-memes-de-petro-xd-v0-93xfvorwwjy81.jpg?auto=webp&s=81fa6f34699c10380ad06897f5854fa680146ca8"
                ),
                Product(
                    6,
                    "Putin",
                    price = 10000,
                    description = "se cree la verga",
                    image = "https://upload.wikimedia.org/wikipedia/commons/8/8b/Vladimir_Putin%2C_a_lomos_de_un_oso_pardo.webp"
                ),
                Product(
                    7,
                    "Kim yom uuuuum",
                    price = 0,
                    description = "es gordo y feo",
                    image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsg3O3AcuuivUVF_XUjgD9Jb-vum936YtGXw&s"
                ),
            )
    }

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