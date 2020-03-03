package com.example.day_trip_planner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(val entries: List<Entry>) : RecyclerView.Adapter<EntryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEntry = entries[position]

        holder.name.setText(currentEntry.name)
        holder.pricePoint.setText(currentEntry.pricePoint)
        holder.rating.setText(currentEntry.rating)
        holder.address.setText(currentEntry.address)
        holder.phone.setText(currentEntry.phone)
        holder.url.setText(currentEntry.url)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name)
        val pricePoint: TextView = itemView.findViewById(R.id.pricePoint)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val address: TextView = itemView.findViewById(R.id.address)
        val phone: TextView = itemView.findViewById(R.id.phone)
        val url: TextView = itemView.findViewById(R.id.url)
    }
}