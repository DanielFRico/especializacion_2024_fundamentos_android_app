package co.unab.edu.daferile.appfundamentos.productdetail.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import co.unab.edu.daferile.appfundamentos.StoreAppDestinations
import co.unab.edu.daferile.appfundamentos.core.ui.model.Product
import co.unab.edu.daferile.appfundamentos.productdetail.viewmodel.ProductDetailViewModel
import coil.compose.AsyncImage

@Composable
fun ProductDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: ProductDetailViewModel,
    modifier: Modifier
) {
    val product: Product by viewModel.getProductById(id).observeAsState(Product(0, "0", 0, "", ""))

    val context = LocalContext.current
    Log.i("holaaaa", product.name)
    if (product.id != 0) {
        ConstraintLayout(
            modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            val (imgProduct, tfName, tfPrice, tfDescription, btnAdd, btnCancel) = createRefs()
            AsyncImage(
                model = product.image, contentDescription = product.image, modifier = Modifier
                    .size(100.dp)
                    .constrainAs(imgProduct) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)

                    }, contentScale = ContentScale.Crop
            )
            Text(
                text = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(tfName) {
                        top.linkTo(imgProduct.bottom, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
            )
            Text(
                text = "$${product.price}",
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(tfPrice) {
                        top.linkTo(tfName.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
            )
            Text(
                text = product.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(tfDescription) {
                        top.linkTo(tfPrice.bottom, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
            )
            Button(
                onClick = {
                    navController.navigate(
                        StoreAppDestinations.UpdateProductDestination.createRoute(
                            id
                        )
                    ) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                modifier = Modifier.constrainAs(btnAdd) {
                    top.linkTo(tfDescription.bottom, margin = 32.dp)
                    start.linkTo(btnCancel.end, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }) {
                Text(text = "Editar")
            }
            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .constrainAs(btnCancel) {
                        top.linkTo(tfDescription.bottom, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(btnAdd.start, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(end = 8.dp)
            ) {
                Text(text = "Regresar")
            }
            createHorizontalChain(btnCancel, btnAdd, chainStyle = ChainStyle.Packed)
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}