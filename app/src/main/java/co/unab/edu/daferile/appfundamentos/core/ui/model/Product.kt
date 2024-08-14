package co.unab.edu.daferile.appfundamentos.core.ui.model

open class Product(
    var id: Int = System.currentTimeMillis().hashCode(),
    var name: String,
    var price: Int,
    var description: String,
    var image: String
) {

    override fun toString(): String {
        return "el nombre del propducto es $name, su precio es $price, su descripcion es $description su foto es $image"
    }
}