package com.example.exchangecurrency.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangecurrency.R
import com.example.exchangecurrency.models.Gold
import kotlinx.android.synthetic.main.gold_card_view_layout.view.*

class GoldRecyclerViewAdapter : RecyclerView.Adapter<GoldViewHolder>() {

    private var items: MutableList<Gold> = mutableListOf()
    private lateinit var myContext: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): GoldViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val goldRow = layoutInflater.inflate(R.layout.gold_card_view_layout, viewGroup, false)
        myContext = viewGroup.context
        return GoldViewHolder(goldRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GoldViewHolder, position: Int) {
        holder.itemView.run {
            card_view_gold_data_id.text = items[position].data
            card_view_gold_price_id.text = items[position].cena + " zł"
        }
    }

    fun updateItemList(list: List<Gold>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}

class GoldViewHolder(private val view: View) : RecyclerView.ViewHolder(view)