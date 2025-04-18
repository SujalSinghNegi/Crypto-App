package com.example.cryptoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cryptoapp.databinding.ActivityMainBinding
import org.json.JSONObject
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var itemData: ArrayList<ItemData>
    val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        itemData= ArrayList<ItemData>()
        binding.recycle1.apply {
           layoutManager= LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter= RvAdapter(itemData)
        }
        apiData()

        binding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(s: Editable?) {
                val filterData= ArrayList<ItemData> ()
                for (item in itemData){
                    if (item.name.lowercase().contains(s.toString().lowercase())){
                        filterData.add(item)
                    }

                }
                if(filterData.isEmpty()){
                    Toast.makeText(this@MainActivity, "No Data Found", Toast.LENGTH_LONG).show()
                }else{
                    binding.recycle1.adapter= RvAdapter(filterData)
                }
            }

        })

    }
    private fun apiData(){
            val queue = Volley.newRequestQueue(this)

            val stringRequest= object :StringRequest
                (
                Request.Method.GET,
                url,{
                    response ->

                    Log.d("response", response.toString())
                    try {
                        val jsonObj = JSONObject(response)
                        val dataArray= jsonObj.getJSONArray("data")
                        for ( i in 0.. dataArray.length()-1){
                              Log.d("response", response.toString())

                            val dataObject= dataArray.getJSONObject(i)
                            val symbol = dataObject.getString("symbol")
                            val name = dataObject.getString("name")
                            val quote = dataObject.getJSONObject("quote")
                            val USD = quote.getJSONObject("USD")
                            val price = String.format("$"+"%.2f", USD.getDouble("price"))

                            itemData.add(ItemData(name, symbol, price.toString()))
                        }
                      //  itemData.add(ItemData("Bitcoin2", "BTC2", "$40,42000"))
                        binding.recycle1.adapter?.notifyItemInserted(itemData.size-1)
                        binding.progressBar.visibility = View.GONE

                    }catch (e: Exception){
                        Toast.makeText(this, "Error 1", Toast.LENGTH_LONG).show()
                    }

                },
                 { error ->
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }

            )
            {


                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                  //  headers["Accept"] = "application/json"
                    headers["X-CMC_PRO_API_KEY"] =
                        "ce906a43-0073-4e79-be82-4c3879c3327e"
                    return headers
                }
            }


            queue.add(stringRequest)
        }
}