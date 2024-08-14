package co.unab.edu.daferile.appfundamentos.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var price: Int,
    var description: String,
    var image: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMm4n0xDPUo-MkJrZ_5VNhR-bxcnYCPLTbOA&s"
)