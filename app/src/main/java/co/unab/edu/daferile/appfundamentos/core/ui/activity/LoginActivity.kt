package co.unab.edu.daferile.appfundamentos.core.data.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.unab.edu.daferile.appfundamentos.StoreAppDestinations.*
import co.unab.edu.daferile.appfundamentos.core.ui.activity.SingUpActivity
import co.unab.edu.daferile.appfundamentos.login.screen.LoginScreen
import co.unab.edu.daferile.appfundamentos.signup.ui.screen.SignUpScreen
import co.unab.edu.daferile.appfundamentos.ui.theme.AppFundamentosTheme
import co.unab.edu.daferile.appfundamentos.login.viewmodel.LoginViewModel
import co.unab.edu.daferile.appfundamentos.signup.ui.viewmodel.SingUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val signupViewModel: SingUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate")
        setContent {
            AppFundamentosTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = LoginDestination.route
                ) {
                    composable(route = LoginDestination.route) {
                        LoginScreen(navController, loginViewModel)
                    }
                    composable(route = SignUpDestination.route) {
                        SignUpScreen(navController, signupViewModel)
                    }
                }
            }
        }
    }

    fun goToSingUp() {
        val intent = Intent(this, SingUpActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
    }
}

@Preview
@Composable
fun MyConstraintLayout() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (boxYellow, boxBlue, boxRed) = createRefs()
        Box(modifier = Modifier
            .background(color = Color.Yellow)
            .size(100.dp)
            .constrainAs(boxYellow) {
                bottom.linkTo(parent.bottom)
                start.linkTo(boxRed.start)
                top.linkTo(parent.top)
            }) {
            Text(text = "holalalalalaal")
        }
        Box(modifier = Modifier
            .background(color = Color.Blue)
            .size(100.dp)
            .constrainAs(boxBlue) {
                top.linkTo(boxYellow.bottom)
                end.linkTo(boxYellow.start)
            })
        Box(modifier = Modifier
            .background(color = Color.Red)
            .size(100.dp)
            .constrainAs(boxRed) {
                top.linkTo(boxYellow.bottom)
                start.linkTo(boxYellow.end)
            })
        createHorizontalChain(boxBlue, boxRed, chainStyle = ChainStyle.Packed)
        createVerticalChain(boxYellow, boxRed, chainStyle = ChainStyle.Packed)
    }
}