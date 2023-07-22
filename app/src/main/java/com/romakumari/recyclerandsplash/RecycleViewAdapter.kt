package com.romakumari.recyclerandsplash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class RecycleViewAdapter(var list:ArrayList<NotesData>,var listInterface: listInterface): RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvName=view.findViewById<TextView>(R.id.tvName)
        var tvRollno=view.findViewById<TextView>(R.id.tvRollno)
        var BtnUpdate =view.findViewById<Button>(R.id.BtnUpdate)
        var Btndelete=view.findViewById<Button>(R.id.Btndelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): RecycleViewAdapter.ViewHolder {
        var view=LayoutInflater.from(parent.context)
            .inflate(R.layout.itemlayout,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecycleViewAdapter.ViewHolder, position: Int) {
        holder.tvName.setText(list[position].title)
        holder.tvRollno.setText(list[position].description)
        holder.BtnUpdate.setOnClickListener {
            listInterface.onUpdateClick(list[position], position)
        }
            holder.Btndelete.setOnClickListener {
                listInterface.onDeleteClick(list [position],position)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }

}


