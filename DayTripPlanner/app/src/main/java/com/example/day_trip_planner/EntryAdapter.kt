package com.example.day_trip_planner

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.RatingBar
import androidx.core.view.isInvisible
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
        holder.address.setText(currentEntry.address)
        holder.address2.setText(currentEntry.address2)
        holder.ratingBar.setRating(currentEntry.rating.toFloat())

        if(currentEntry.phone == null)
            holder.phoneButton.isInvisible = true
        else
            holder.phoneButton.setOnClickListener{
                try {
                    val phoneIntent: Intent = Uri.parse("tel:" + currentEntry.phone).let { number ->
                        Intent(Intent.ACTION_DIAL, number)
                    }
                    it.getContext().startActivity(phoneIntent)
                }
                catch(e: Exception) {
                    e.printStackTrace()
                }
            }

        holder.urlButton.setOnClickListener{
            try {
                val urlIntent: Intent = Uri.parse(currentEntry.url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                it.getContext().startActivity(urlIntent)
            }
            catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name)
        val pricePoint: TextView = itemView.findViewById(R.id.pricePoint)
        val address: TextView = itemView.findViewById(R.id.address)
        val address2: TextView = itemView.findViewById(R.id.address2)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val phoneButton: ImageButton = itemView.findViewById(R.id.phoneButton)
        val urlButton: ImageButton = itemView.findViewById(R.id.urlButton)
    }
}