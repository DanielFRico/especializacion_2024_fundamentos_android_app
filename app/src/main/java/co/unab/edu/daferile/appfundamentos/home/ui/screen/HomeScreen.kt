package co.unab.edu.daferile.appfundamentos.home.ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import co.unab.edu.daferile.appfundamentos.StoreAppDestinations.ProductDetailDestination
import co.unab.edu.daferile.appfundamentos.home.ui.viewmodel.HomeViewModel
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.home.ui.ProductListUIState
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collect

@Composable
fun HomeScreen(modifier: Modifier, viewModel: HomeViewModel, navController: NavController) {
    val context = LocalContext.current
    // val productList: List<Product> by viewModel.productList.observeAsState(emptyList())
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<ProductListUIState>(
        initialValue = ProductListUIState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state -> value = state }
        }
    }
    when (uiState) {
        is ProductListUIState.Error -> {}
        ProductListUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is ProductListUIState.Success -> {
            val productList = (uiState as ProductListUIState.Success).productsList
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productList) {
                    ProductItem(product = it, onLongPressItem = {
                        viewModel.removeProduct(it)
                    }) { product ->
                        navController.navigate(ProductDetailDestination.createRoute(product.id)) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ProductItem(
    product: Product,
    onLongPressItem: (Product) -> Unit,
    onSelectedItem: (Product) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(150.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    onSelectedItem(product)
                }, onLongPress = {
                    onLongPressItem(product)
                })
            }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (image, name, price) = createRefs()
            AsyncImage(
                model = product.image,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 16.dp)
                    })
            Text(text = product.name, modifier = Modifier.constrainAs(name) {
                start.linkTo(image.end)
                end.linkTo(parent.end)
                top.linkTo(image.top)
                bottom.linkTo(price.top)
            })
            Text(text = product.description, modifier = Modifier.constrainAs(price) {
                start.linkTo(image.end)
                end.linkTo(parent.end)
                top.linkTo(image.bottom)
                bottom.linkTo(name.bottom)
            })
        }
    }
}

@Preview
@Composable
fun TestProductItem() {
    /*
    val context = LocalContext.current
    ProductItem(product = loadProducts().first()) { product ->
        Toast.makeText(context, product.toString(), Toast.LENGTH_LONG).show()
    }
    */
}

