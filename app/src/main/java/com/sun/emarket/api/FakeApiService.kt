package com.sun.emarket.api

import com.sun.emarket.model.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeApiService {

    @GET("products/categories")
    fun getCategories(): Call<List<String>>

    @GET("products/category/{categoryName}")
    fun getProductsInCategory(@Path("categoryName") categoryName: String): Call<List<Product>>

    companion object {
        private const val BASE_URL = "https://fakestoreapi.com/"

        fun create(): FakeApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(FakeApiService::class.java)
        }
    }
}
