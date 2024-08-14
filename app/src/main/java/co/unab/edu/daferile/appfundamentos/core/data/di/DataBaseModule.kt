package co.unab.edu.daferile.appfundamentos.core.data.di

import android.content.Context
import androidx.room.Room
import co.unab.edu.daferile.appfundamentos.core.data.local.StoreAppDatabase
import co.unab.edu.daferile.appfundamentos.core.data.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun providesStoreAppDatabase(@ApplicationContext context: Context): StoreAppDatabase {
        return Room.databaseBuilder(context, StoreAppDatabase::class.java, "storeapp.db").build()
    }

    @Provides
    fun providesProductDAO(storeAppDatabase: StoreAppDatabase): ProductDao {
        return storeAppDatabase.productDAO()
    }
}