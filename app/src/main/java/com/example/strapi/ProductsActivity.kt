package com.example.strapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsActivity : AppCompatActivity() {

    var productService : ProductsServices? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val accessToken : String = getAccessToken()!!

        productService = RetrofitHelper.getAuthenticatedInstance(getString(R.string.base_url), accessToken)
        productService!!.getProducts(100).enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("WW", t.message!!)
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(!response.isSuccessful) {
                    Log.d("LL", response.message())
                    return
                }
                val recView = findViewById<RecyclerView>(R.id.recycler_view)
                val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this@ProductsActivity)
                recView.layoutManager = layoutManager

                recView.addItemDecoration(
                    DividerItemDecoration(
                        recView.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
                val adapter = TaskListAdapter(onClickListener = this@ProductsActivity::onRecyclerClick)
                adapter.setData(response.body()!!)
                recView.adapter = adapter
            }
        })
    }

    private fun onRecyclerClick(task: Product) {
    }

    private fun getAccessToken(): String? {
        return getSharedPreferences(MainActivity.DEFAULT_PREDERENCES_KEY, Context.MODE_PRIVATE)
            .getString(getString(R.string.access_token_key), "")
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add -> {
            val intent = Intent(this@ProductsActivity, IngresarActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true;
    }
}