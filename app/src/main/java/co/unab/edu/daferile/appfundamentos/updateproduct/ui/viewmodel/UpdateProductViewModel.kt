package co.unab.edu.daferile.appfundamentos.updateproduct.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.home.domain.AddProductUseCase
import co.unab.edu.daferile.appfundamentos.updateproduct.domain.UpdateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProductViewModel @Inject constructor(private val updateProductUseCase: UpdateProductUseCase) :
    ViewModel() {

    fun updateProduct(name: String, price: String, description: String, image: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = Product(0, name, price.toInt(), description, image)
            updateProductUseCase(product)
        }
    }
}