package com.isfan17.restoreviews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.isfan17.restoreviews.R
import com.isfan17.restoreviews.models.DrinksItem
import com.isfan17.restoreviews.models.FoodsItem

class MenuAdapter(
    private val listFoods: List<FoodsItem>,
    private val listDrinks: List<DrinksItem>
): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var listMenu: List<Any> = listFoods + listDrinks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false))
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        when (val item = listMenu[position])
        {
            is FoodsItem -> {
                holder.tvName.text = item.name
                holder.tagFood.visibility = View.VISIBLE
                holder.tagDrink.visibility = View.GONE
            }
            is DrinksItem -> {
                holder.tvName.text = item.name
                holder.tagFood.visibility = View.GONE
                holder.tagDrink.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = listFoods.size + listDrinks.size

    inner class MenuViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tagDrink: TextView = view.findViewById(R.id.tag_drink)
        val tagFood: TextView = view.findViewById(R.id.tag_food)
    }

}