package com.example.exchangecurrency.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangecurrency.databinding.HomeCardViewLayoutBinding
import com.example.exchangecurrency.models.AllCurrency

class HomeRecyclerViewAdapter : RecyclerView.Adapter<HomeViewHolder>() {

    private var items: MutableList<AllCurrency.Rate> = mutableListOf()
    lateinit var myContext: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HomeViewHolder {
        val binding = HomeCardViewLayoutBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        myContext = viewGroup.context
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) =
        holder.filData(items[position])

    fun updateItemList(list: List<AllCurrency.Rate>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}

class HomeViewHolder(private val binding: HomeCardViewLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun filData(item: AllCurrency.Rate) {
        binding.rate = item
    }
}
