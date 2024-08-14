package co.unab.edu.daferile.appfundamentos.productdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.productdetail.domain.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val getProductUseCase: GetProductUseCase) :
    ViewModel() {
    fun getProductById(id: Int): LiveData<Product> = getProductUseCase(id)
}