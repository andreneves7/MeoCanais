package com.example.meocanais

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (val listaCanais: List<String>) : RecyclerView.Adapter<MyAdapter.ExampleViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_card, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun getItemCount(): Int = listaCanais.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listaCanais[position]

        holder.nomeCanal.text = currentItem
//        holder.programaAtual.text = currentItem.
//        holder.programaNext.text = currentItem.stock

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
    }


}