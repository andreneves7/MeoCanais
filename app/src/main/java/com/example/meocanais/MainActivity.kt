package com.example.meocanais

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    var listaFiltradaItem: MutableList<Canais> = ArrayList()

    val clientesService = ServiceBuilder.buildService(EndPoints::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        recyclerView = findViewById(R.id.recycleViewCanais)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        MyAdapter = MyAdapter(listaItem)


        val requestCall = clientesService.getChannels("json", "AND",
            "substringof%28%27MEO_Mobile%27%2CAvailableOnChannels%29%20and%20IsAdult%20eq%20false",
        "ChannelPosition%20asc", "allpages", 0)

        requestCall.enqueue(object : Callback<CanaisList> {
            override fun onResponse(call: Call<CanaisList>, response: Response<CanaisList>) {
                if (response.isSuccessful) {
                    val itemList = response.body()!!.canalList



                    for(i in itemList){


        BuscaProgramas(i)

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

    private fun BuscaProgramas(i: Canais) {
        val requestProg = clientesService.getProg( "CallLetter eq '${i.Title}'","AND",
            "StartDate%20asc")



        requestProg.enqueue(object : Callback<ProgramaList> {
            override fun onResponse(call: Call<ProgramaList>, response: Response<ProgramaList>) {
                if (response.isSuccessful) {
                    val itemList = response.body()!!.programaList

                    val controlo = i.Title
                    var index = 1
                    var atual = ""

                        Log.d("endpoint", "${i.Title}")

                    for(a in itemList){

                        if (controlo == i.Title && index == 2){
                            listaFiltradaItem.add(Canais(i.Title,atual, a.Title))
                        }
                        else
                        {
                            atual = a.Title



                            index ++
                        }




                        Log.d("endpoint", "a= ${a.Title}")

                    }
                            recyclerView.adapter = MyAdapter(listaFiltradaItem)
                } else {
                    Toast.makeText(this@MainActivity, "Erro a carregar os itens", Toast.LENGTH_SHORT).show()
                    Log.d("endpoint", "${response}")
                }
            }

            override fun onFailure(call: Call<ProgramaList>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Erro: $t", Toast.LENGTH_LONG).show()
                Log.d("endpoint", "$t")
            }

        })
    }


}