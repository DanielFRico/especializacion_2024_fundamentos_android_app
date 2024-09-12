package co.unab.edu.daferile.appfundamentos.signup.ui.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import co.unab.edu.daferile.appfundamentos.core.ui.activity.MainActivity
import co.unab.edu.daferile.appfundamentos.signup.ui.viewmodel.SingUpViewModel
import coil.compose.AsyncImage

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SingUpViewModel
) {
    var name: String by rememberSaveable {

        mutableStateOf("")
    }
    var password: String by rememberSaveable {

        mutableStateOf("")
    }
    var email: String by rememberSaveable {

        mutableStateOf("")
    }
    var document: String by rememberSaveable {

        mutableStateOf("")
    }

    var image: String by rememberSaveable {
        mutableStateOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFrCyciG9jzzJdm-liLba4ERTeoy5O94A9QA&s")
    }
    val context = LocalContext.current
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState(initialValue = false, key1 = lifeCycle, key2 = viewModel) {
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.onCreateAccount.collect { isCreated ->
                value = isCreated
            }
        }
    }

    when (uiState) {
        true -> {
            LaunchedEffect(Unit) {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }
        false -> {
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                val (imgUser, tfName, tfDocument, tfImage, tfPass, btnAdd, btnCancel, tfEmail) = createRefs()
                AsyncImage(
                    model = image, contentDescription = name, modifier = Modifier
                        .size(100.dp)
                        .constrainAs(imgUser) {
                            top.linkTo(parent.top, margin = 32.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)

                        }, contentScale = ContentScale.Crop
                )
                TextField(
                    value = name,
                    onValueChange = { productName -> name = productName },
                    label = { Text(text = "Nombre:") },
                    placeholder = { Text(text = "Nombre suyo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfName) {
                            top.linkTo(imgUser.bottom, margin = 32.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = password,
                    onValueChange = { newPass -> password = newPass },
                    label = { Text(text = "Contrasena:") },
                    placeholder = { Text(text = "Escriba su contrasena") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfPass) {
                            top.linkTo(tfName.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    ),
                    visualTransformation = PasswordVisualTransformation()

                )
                TextField(
                    value = email,
                    onValueChange = { newEmail -> email = newEmail },
                    label = { Text(text = "ru correo") },
                    placeholder = { Text(text = "tu correo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfEmail) {
                            top.linkTo(tfPass.bottom, margin = 32.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    ),
                    singleLine = true,
                )
                TextField(
                    value = document,
                    onValueChange = { newDocument -> document = newDocument },
                    label = { Text(text = "tu cedula") },
                    placeholder = { Text(text = "tu cedula") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfDocument) {
                            top.linkTo(tfEmail.bottom, margin = 32.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    ),
                    singleLine = true,
                )
                Button(
                    onClick = {
                        viewModel.createAccount(name, document, password, email, image)
                    },
                    modifier = Modifier.constrainAs(btnAdd) {
                        top.linkTo(tfDocument.bottom, margin = 32.dp)
                        start.linkTo(btnCancel.end, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }) {
                    Text(text = "Crear usuario")
                }
                OutlinedButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .constrainAs(btnCancel) {
                            top.linkTo(tfImage.bottom, margin = 32.dp)
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
        }
    }

}

