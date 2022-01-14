package com.example.meocanais

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    private val listaItem = ArrayList<Canais>()
    private lateinit var MyAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        recyclerView = findViewById(R.id.recycleViewCanais)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        MyAdapter = MyAdapter(listaItem)

        val clientesService = ServiceBuilder.buildService(EndPoints::class.java)
        val requestCall = clientesService.getChannels("json", "AND",
            "substringof%28%27MEO_Mobile%27%2CAvailableOnChannels%29%20and%20IsAdult%20eq%20false",
        "ChannelPosition%20asc", "allpages", 0)

        requestCall.enqueue(object : Callback<CanaisList> {
            override fun onResponse(call: Call<CanaisList>, response: Response<CanaisList>) {
                if (response.isSuccessful) {
                    val itemList = response.body()!!.canalList

                    var listaFiltradaItem: MutableList<Canais> = ArrayList()

                    for(i in itemList){

                        listaFiltradaItem.add(i)

                        Log.d("endpoint", "${i.Title}")


//                        recyclerView.adapter = ArrayAdapter(this@MainActivity, R.layout.custom_card,listaFiltradaItem)
                            recyclerView.adapter = MyAdapter(listaFiltradaItem)

                    }
                } else {
                    Toast.makeText(this@MainActivity, "Erro a carregar os itens", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CanaisList>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Erro: $t", Toast.LENGTH_LONG).show()
                Log.d("endpoint", "$t")
            }

        })


    }
}