package com.example.strapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarActivity : AppCompatActivity() {
    var productService : ProductsServices? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar)
        productService = RetrofitHelper.getInstance(getString(R.string.base_url))


        findViewById<Button>(R.id.insert).setOnClickListener {
            val name = findViewById<TextView>(R.id.nameInsert).text.toString()
            val price = findViewById<TextView>(R.id.precioInsert).text.toString()
            val upcInsert = findViewById<TextView>(R.id.upcInserr).text.toString()
            val p = Product(price.toFloat(), upcInsert, name)

            productService!!.insertProducts(p).enqueue(object : Callback<Product> {
                override fun onFailure(call: Call<Product>, t: Throwable) {
                    Log.d("WW", t.message!!)
                }

                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    if (!response.isSuccessful) {
                        Log.d("LL", response.message())
                        return
                    }
                    Toast.makeText(baseContext, "Exito", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}