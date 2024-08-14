package co.unab.edu.daferile.appfundamentos.core.ui.model

class ProdcutDiscount(id: Int, name: String, price: Int, discount: Int, myLambda: (Int) -> String) :
    Product(id, name, price, "", "")