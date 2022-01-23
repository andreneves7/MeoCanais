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

    var endHasBeenReached = false
    var totalItemCount = 0
    var lastVisible = 0
    var frist = 0

    var listaPronta = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        recyclerView = findViewById(R.id.recycleViewCanais)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        MyAdapter = MyAdapter(listaItem)

//        val atual = "Traz Prá Frente T6 - Ep.33"
//
//        val imagem = CarregarCapa(atual)



        BuscaCanais()

        Log.d("a", "$listaPronta")

        lastVisible = layoutManager.findLastVisibleItemPosition()
        frist = layoutManager.findFirstVisibleItemPosition()



        Log.d("fim" ,"$lastVisible")

//        BuscaProgramas(frist,lastVisible)





        Log.d("a", "$listaPronta")
        Log.d("fim" ,"teste = $frist")

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
//                val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                totalItemCount = layoutManager.itemCount
                lastVisible = layoutManager.findLastVisibleItemPosition()
                frist = layoutManager.findFirstVisibleItemPosition()
                Log.d("fim" ,"$lastVisible")
                Log.d("fim" ,"ola = $frist")

                endHasBeenReached = lastVisible + 2 >= totalItemCount
//                Log.d("fim" ,"$endHasBeenReached")
                    Log.d("fim" ,"$lastVisible")
                    Log.d("fim" ,"ola = $frist")
                if (totalItemCount > 0 && endHasBeenReached) {
                    Log.d("fim" ,"$totalItemCount")

                    BuscaCanais()

                }

            }
        })

    }

    private  fun BuscaCanais(){

        var a : Int = listaFiltradaItem.count()

        val requestCall = clientesService.getChannels("json", "AND",
            "substringof('MEO_Mobile'2CAvailableOnChannels) and IsAdult eq false",
            "ChannelPosition asc", "allpages", a)

        var atual = ""
        var imagem = ""

        Log.d("a", "${requestCall.request().url()}")

        Log.d("lista", "${listaFiltradaItem.count()}")

        requestCall.enqueue(object : Callback<CanaisList> {
            override fun onResponse(call: Call<CanaisList>, response: Response<CanaisList>) {
                if (response.isSuccessful) {
                    val itemList = response.body()!!.canalList



                    for(i in itemList){
                        Log.d("endpoint", "${i.Title}")


//                        listaFiltradaItem.add(Canais(i.CallLetter,i.Title,atual, atual,imagem ))
                        BuscaProgramas(i)


                    }
//                    recyclerView.adapter = MyAdapter(listaFiltradaItem)

                } else {
                    Toast.makeText(this@MainActivity, "Erro a carregar os itens", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CanaisList>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Erro: $t", Toast.LENGTH_LONG).show()
                Log.d("endpoint", "$t")
            }

        })



    }

    private fun BuscaProgramas(i: Canais) {

        val requestProg = clientesService.getProg( "CallLetter eq '${i.CallLetter}'","AND",
            "StartDate%20asc")



        requestProg.enqueue(object : Callback<ProgramaList> {
            override fun onResponse(call: Call<ProgramaList>, response: Response<ProgramaList>) {
                if (response.isSuccessful) {
                    val itemList = response.body()!!.programaList

                    val controlo = i.Title
                    var index = 1
                    var atual = ""
                    var imagem = ""

                        Log.d("endpointi", "i = ${i.Title}")

                    for(a in itemList){

                        Log.d("endpointi", "a = ${a.Title}")

                        if (controlo == i.Title && index == 2)
                        {

                            listaFiltradaItem.add(Canais(i.CallLetter,i.Title,atual,a.Title,imagem ))
                        }
                        else
                        {
                            atual = a.Title

                            imagem = CarregarCapa(atual)
                            Log.d("img", "$imagem")

                            index ++
                        }




//                        Log.d("endpointa", "a= ${listaFiltradaItem[index].Title}")

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

    private fun CarregarCapa(atual: String) :String {

        var t = ""

        if (atual.contains("-"))
        {

            t = atual.replace("-", "+-+").replace(".", ".+")
            Log.d("ola", "$t")


        }
        else
        {
            t = atual
        }

        return  t

    }


}