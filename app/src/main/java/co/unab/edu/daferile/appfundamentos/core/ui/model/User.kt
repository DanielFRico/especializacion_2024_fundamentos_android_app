package co.unab.edu.daferile.appfundamentos.core.ui.model

data class User(
    var id: String = "",
    var name: String,
    var document: Long,
    var email: String,
    var image: String
) {
    override fun toString(): String {
        return "El nombre de este man es $name"
    }
}