package com.example.meocanais

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter (val listaCanais: List<Canais>) : RecyclerView.Adapter<MyAdapter.ExampleViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_card, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun getItemCount(): Int = listaCanais.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listaCanais[position]

        holder.nomeCanal.text = currentItem.Title
        Picasso.get().
        load("http://213.13.23.69/wp/cdn-images.online.meo.pt/eemstb/ImageHandler.ashx?evTitle=${currentItem.capa}&chCallLetter=${currentItem.Title}&profile=16_9&width=320").
            into(holder.capa)
        holder.programaAtual.text = currentItem.progNow
        holder.programaNext.text = currentItem.progNext

//        holder.itemView.setOnClickListener{
//
//            val bundle = Bundle()
//            bundle.putString("itemId", currentItem.id)
//            val fragment = DetalhesItemFragment()
//            fragment.arguments = bundle
//
//            val manager: FragmentManager = (it.context as AppCompatActivity).supportFragmentManager
//            manager.beginTransaction()
//                .replace(R.id.frame_layout, fragment)
//                .addToBackStack(null)
//                .commit()
//        }
    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeCanal: TextView = itemView.findViewById(R.id.labelCanal)
        val programaNext: TextView = itemView.findViewById(R.id.labelProgramaNext)
        val programaAtual: TextView = itemView.findViewById(R.id.labelProgramaIN)
        var capa : ImageView = itemView.findViewById(R.id.imgCapa)
    }


}