package com.example.strapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

val TAG: String = "APP_MESSAGE"

class MainActivity : AppCompatActivity() {

    companion object {
        const val DEFAULT_PREDERENCES_KEY : String = "DEFAULT_PREFERENCES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var apiClient = RetrofitHelper.getInstance(getString(R.string.base_url))

//        var credentials = Credentials(
//            emailTextView.text.toString(),
//            passwordTextView.text.toString())

        var credentials = Credentials(
            "console@strapi.io",
            "123456")

        val getTokenRequest = apiClient.GetToken(credentials)

        getTokenRequest.enqueue(object : retrofit2.Callback<TokenInfo> {

            override fun onResponse(
                call: retrofit2.Call<TokenInfo>,
                response: retrofit2.Response<TokenInfo>
            ) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        run {
                            Toast.makeText(this@MainActivity, response.body()!!.jwt, Toast.LENGTH_SHORT ).show()
                            storeAccessToken(response.body()!!.jwt)
                            startActivity(Intent(this@MainActivity, ProductsActivity::class.java))
                        }
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<TokenInfo>, t: Throwable) {
                runOnUiThread {
                    run {
                        Toast.makeText(this@MainActivity, "ERROR" + t.message, Toast.LENGTH_SHORT ).show()
                    }
                }
            }
        })
    }

    fun storeAccessToken(token: String) {
        val sharedPreferencesEdit = getSharedPreferences(DEFAULT_PREDERENCES_KEY, Context.MODE_PRIVATE).edit()
        sharedPreferencesEdit.putString(getString(R.string.access_token_key), token)
        sharedPreferencesEdit.commit()
        Log.i(TAG, "Token stored: " + token)
    }

    private fun getAccessToken(): String? {
        return getSharedPreferences(DEFAULT_PREDERENCES_KEY, Context.MODE_PRIVATE)
            .getString(getString(R.string.access_token_key), "")
    }
}