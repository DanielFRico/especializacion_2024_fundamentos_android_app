package co.unab.edu.daferile.appfundamentos

sealed class StoreAppDestinations(val title: String, val route: String) {
    data object LoginDestination : StoreAppDestinations("Login", "Login")
    data object SignUpDestination : StoreAppDestinations("SingUp", "SingUp")
    data object HomeDestination : StoreAppDestinations("Products List", "ProductsList")
    data object MyProfile : StoreAppDestinations("My Profile", "MyProfile")
    data object Users : StoreAppDestinations("User", "Users")
    data object CreateProduct : StoreAppDestinations("Create Product", "CreateProduct")
    data object ProductDetailDestination :
        StoreAppDestinations("Product Details", "product-detail/{id}") {
        fun createRoute(id: Int) = "product-detail/$id"
    }
    data object UpdateProductDestination :
        StoreAppDestinations("Product update", "update-product/{id}") {
        fun createRoute(id: Int) = "update-product/$id"
    }

    fun mainScreens() = listOf<StoreAppDestinations>(HomeDestination, MyProfile, CreateProduct, UpdateProductDestination)
}