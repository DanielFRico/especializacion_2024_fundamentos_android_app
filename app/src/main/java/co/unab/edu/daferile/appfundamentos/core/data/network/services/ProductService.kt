package co.unab.edu.daferile.appfundamentos.core.data.network.services

import co.unab.edu.daferile.appfundamentos.core.data.local.entity.ProductEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("products.json")
    fun getAll(): Call<Map<String, ProductEntity>>

    @GET("products/{id}.json")
    fun getById(@Path("id") id: Int): Call<ProductEntity>

    @PUT("product/{id}.json")
    fun add(@Path("id") id: Int, @Body pruductEntity: ProductEntity): Call<Any>
}