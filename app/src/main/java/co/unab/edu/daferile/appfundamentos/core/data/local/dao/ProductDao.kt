package co.unab.edu.daferile.appfundamentos.core.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import co.unab.edu.daferile.appfundamentos.core.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id=:id")
    fun getById(id: Int): LiveData<ProductEntity>

    @Insert
    suspend fun add(product: ProductEntity)

    @Insert
    suspend fun addMany(vararg products: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

}