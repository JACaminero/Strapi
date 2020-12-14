package com.example.strapi

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ProductsServices {
    @POST("auth/local")
    fun GetToken (@Body() credentials: Credentials) : Call<TokenInfo>

    @POST("products")
    fun insertProducts(@Body product: Product): Call<Product>

    @GET("products")
    fun getProducts( @Query("_limit") limit: Int = 10, @Header("Authorization") token : String) : Call<List<Product>>

    @GET("products")
    fun getProducts( @Query("_limit") limit: Int) : Call<List<Product>>
}

class  RetrofitHelper {
    companion object {
        fun getInstance(baseUrl : String) : ProductsServices {

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .build()

            return retrofit.create(ProductsServices::class.java)
        }

        fun getAuthenticatedInstance(baseUrl: String, token: String) : ProductsServices {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor{ chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build()

                    chain.proceed(newRequest)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return  retrofit.create(ProductsServices::class.java)
        }
    }
}

data class  Credentials (
    val identifier : String,
    val password : String
)

public class TokenInfo {
    lateinit var jwt : String
}

data class Product (
    var price: Float,
    val upc: String,
    val name: String
)