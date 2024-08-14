package co.unab.edu.daferile.appfundamentos.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.unab.edu.daferile.appfundamentos.core.data.local.dao.ProductDao
import co.unab.edu.daferile.appfundamentos.core.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class StoreAppDatabase : RoomDatabase() {
    abstract fun productDAO(): ProductDao
}