package co.unab.edu.daferile.appfundamentos.createproduct.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.home.domain.AddProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(private val addProductUseCase: AddProductUseCase) :
    ViewModel() {

    fun addProduct(name: String, price: String, description: String, image: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = Product(
                name = name,
                price = price.toInt(),
                description = description,
                image = image
            )
            addProductUseCase(product)
        }
    }
}