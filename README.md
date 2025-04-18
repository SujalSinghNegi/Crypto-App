Edit Text changed watcher
```
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
```









Learn the Api key use using volley
```
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
                    headers["X-CMC_PRO_API_KEY"] =
                        "Enter you api key here"
                    return headers
                }
            }
            queue.add(stringRequest)
        }
        ```
