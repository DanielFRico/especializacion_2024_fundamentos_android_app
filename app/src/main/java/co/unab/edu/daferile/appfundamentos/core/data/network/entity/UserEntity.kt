package co.unab.edu.daferile.appfundamentos.core.data.network.entity

data class UserEntity(
    var id: String,
    var name: String,
    var document: Long,
    var email: String,
    var image: String
) {
}