package co.unab.edu.daferile.appfundamentos.core.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.unab.edu.daferile.appfundamentos.StoreAppDestinations.*
import co.unab.edu.daferile.appfundamentos.createproduct.screen.CreateProductScreen
import co.unab.edu.daferile.appfundamentos.createproduct.screen.CreateProductViewModel
import co.unab.edu.daferile.appfundamentos.home.ui.screen.HomeScreen
import co.unab.edu.daferile.appfundamentos.home.ui.viewmodel.HomeViewModel
import co.unab.edu.daferile.appfundamentos.productdetail.screen.ProductDetailScreen
import co.unab.edu.daferile.appfundamentos.productdetail.viewmodel.ProductDetailViewModel
import co.unab.edu.daferile.appfundamentos.profile.screen.ProfileScreen
import co.unab.edu.daferile.appfundamentos.profile.viewmodel.ProfileViewModel
import co.unab.edu.daferile.appfundamentos.ui.theme.AppFundamentosTheme
import co.unab.edu.daferile.appfundamentos.updateproduct.ui.screen.UpdateProductScreen
import co.unab.edu.daferile.appfundamentos.updateproduct.ui.viewmodel.UpdateProductViewModel
import co.unab.edu.daferile.appfundamentos.users.screen.UsersScreen
import co.unab.edu.daferile.appfundamentos.users.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private val createProductViewModel: CreateProductViewModel by viewModels()
    private val updateProductViewModel: UpdateProductViewModel by viewModels()
    private val usersViewModel: UserViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ejemplo de corutinas, que es correr diferente procesos en otros hilos
        // que no sean el principal, osea el de la UI
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                Toast.makeText(baseContext, "Coriendo un toast un IO thread", Toast.LENGTH_LONG)
                    .show()
            }

        }
        setContent {
            val navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination
            AppFundamentosTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                currentDestination?.route?.let {
                                    Text(
                                        text = it,
                                        color = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ) {
                            NavigationBarItem(
                                selected = HomeDestination.route == currentDestination?.route,
                                onClick = {
                                    navController.navigate(HomeDestination.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                    }
                                },
                                label = { Text(text = "Home", color = Color.White) },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = "home"
                                    )
                                })
                            NavigationBarItem(
                                selected = MyProfile.route == currentDestination?.route,
                                onClick = {
                                    navController.navigate(MyProfile.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                label = { Text(text = "Mi profile", color = Color.White) },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = "profile"
                                    )
                                })
                            NavigationBarItem(
                                selected = Users.route == currentDestination?.route,
                                onClick = {
                                    navController.navigate(Users.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                label = { Text(text = "User", color = Color.White) },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Face,
                                        contentDescription = "usersß"
                                    )
                                })
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navController.navigate(CreateProduct.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeDestination.route
                    ) {
                        composable(HomeDestination.route) {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                homeViewModel,
                                navController
                            )
                        }
                        composable(MyProfile.route) {
                            ProfileScreen(profileViewModel)
                        }
                        composable(Users.route) {
                            UsersScreen(modifier = Modifier.padding(innerPadding), usersViewModel)
                        }
                        composable(CreateProduct.route) {
                            CreateProductScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                createProductViewModel
                            )
                        }
                        composable(
                            ProductDetailDestination.route,
                            arguments = listOf(navArgument("id") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            ProductDetailScreen(
                                id = backStackEntry.arguments?.getInt("id") ?: 0,
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                viewModel = productDetailViewModel
                            )
                        }
                        composable(
                            UpdateProductDestination.route,
                            arguments = listOf(navArgument("id") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            UpdateProductScreen(
                                id = backStackEntry.arguments?.getInt("id") ?: 0,
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                viewModel = updateProductViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}