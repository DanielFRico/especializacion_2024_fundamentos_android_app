package co.unab.edu.daferile.appfundamentos.login.screen

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import co.unab.edu.daferile.appfundamentos.login.viewmodel.LoginViewModel
import co.unab.edu.daferile.appfundamentos.core.ui.activity.MainActivity
import co.unab.edu.daferile.appfundamentos.R
import co.unab.edu.daferile.appfundamentos.StoreAppDestinations
import co.unab.edu.daferile.appfundamentos.login.viewmodel.LoginUIState
import coil.compose.AsyncImage

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current
    val uiState by produceState<LoginUIState>(
        initialValue = LoginUIState.Default,
        lifeCycle,
        viewModel
    ) {
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { isLogin -> value = isLogin }
        }
    }
    when (uiState) {
        LoginUIState.Default -> {
        }

        LoginUIState.Error -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Invalidos", Toast.LENGTH_LONG).show()
            }
        }

        LoginUIState.Loading -> {}
        LoginUIState.Success -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Iniciando sesion", Toast.LENGTH_LONG).show()
            }
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }
    Column {
        HeaderLogin()
        BodyLogin(viewModel)
        Spacer(modifier = Modifier.weight(1f))
        FooterLogin(navController)
    }

}

@Composable
fun HeaderLogin() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (imLogin, txtLogin) = createRefs()
        AsyncImage(model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7b3ArkB7Oocsco2nxglnEoCAupiepqWfpmQ&s",
            contentDescription = "image",
            modifier = Modifier
                .constrainAs(imLogin) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .size(200.dp),
            contentScale = ContentScale.Fit)
        Text(text = stringResource(R.string.loginTitle), modifier = Modifier
            .constrainAs(txtLogin) {
                top.linkTo(imLogin.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth(), fontSize = 18.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun BodyLogin(viewModel: LoginViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val context = LocalContext.current
        val (tfMail, tfPassword, btnSignIn) = createRefs()
        val emailValue by viewModel.email.observeAsState("")
        val passwordValue by viewModel.password.observeAsState("")

        OutlinedTextField(leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.baseline_password_24),
                contentDescription = "email"
            )
        },
            value = emailValue,
            onValueChange = { viewModel.onLoginChange(it, passwordValue) },
            label = {
                Text(
                    text = "Email here"
                )
            },
            modifier = Modifier
                .constrainAs(tfMail) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .fillMaxWidth())
        OutlinedTextField(trailingIcon = {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "eye")
        },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "start-eye")
            },
            value = passwordValue,
            onValueChange = { viewModel.onLoginChange(emailValue, it) },
            visualTransformation = PasswordVisualTransformation(),
            label = {
                Text(
                    text = "Password here"
                )
            },
            modifier = Modifier
                .constrainAs(tfPassword) {
                    top.linkTo(tfMail.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .fillMaxWidth())
        Button(
            onClick = { viewModel.checkIsValidLogin() },
            modifier = Modifier.constrainAs(btnSignIn) {
                top.linkTo(tfPassword.bottom)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }) {
            Text(text = "Loguearse", color = Color.White)
        }
    }
}


@Composable
fun FooterLogin(navController: NavController) {
    val context = LocalContext.current

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (divider, txtSignUp) = createRefs()
        HorizontalDivider(modifier = Modifier.constrainAs(divider) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        Text(text = "No tienes cuenbta? registrate es gratis",
            color = Color.Gray,
            textDecoration = TextDecoration.Underline,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(txtSignUp) {
                    top.linkTo(divider.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable { navController.navigate(StoreAppDestinations.SignUpDestination.route) })
    }
}

