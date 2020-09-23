package com.doodleblue.doodleblue.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.doodleblue.doodleblue.R
import com.doodleblue.doodleblue.fragments.PricesFragment
import com.doodleblue.doodleblue.models.data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_row_data.view.*
import java.util.*

class DataAdapter(private val list: ArrayList<data>, val context:Context,val pricesFragment: PricesFragment) : RecyclerView.Adapter<DataAdapter.MyViewHolder>(),
    Filterable {
    var dataFilterList = ArrayList<data>()

    init {
        dataFilterList = list
        context
        pricesFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_row_data, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {

        return dataFilterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_number.text = dataFilterList[position].rank.toString().trim()
        holder.itemView.tv_name.text = dataFilterList[position].name.trim()
            if(list[position].changePercent24Hr!=null) {
                val changePrice: Double =
                    dataFilterList[position].changePercent24Hr.trim().toDouble()
                val rounded = String.format("%,.2f", changePrice)
                holder.itemView.tv_change.text = "$rounded%"
                if (changePrice < 0.0) {
                    holder.itemView.tv_change.setTextColor(Color.RED)
                }
            }else{
                holder.itemView.tv_change.text="0.00%"
            }
            if(list[position].priceUsd!= null) {
                val priceUsd: Double = dataFilterList[position].priceUsd.trim().toDouble()
                val rounded_price_usd = String.format("%,.4f", priceUsd)
                holder.itemView.tv_price.text = "$$rounded_price_usd"
            }else{
                holder.itemView.tv_price.text="$0.00"
            }
        try {
            //.load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRqVOlpvWbeqwKTNcGFRXNQCMITJ_CeQRzJ7A&usqp=CAU")
            Picasso.get()
                .load(R.drawable.circular_view_image)
                .into(holder.itemView.circular_img_view)
        } catch (ex: Exception) {
            ex.message
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = (pricesFragment).data!!
                } else {
                    val resultList = ArrayList<data>()
                    for (row in list) {
                        if (row.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    dataFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                try {
                    dataFilterList = results?.values as ArrayList<data>
                    //(context as (PricesFragment)).searchData(dataFilterList as ArrayList<data>?)
                    pricesFragment.searchData(dataFilterList as ArrayList<data>?)
                    notifyDataSetChanged()
                }catch (ex:Exception){
                    ex.message
                }
            }
        }
    }
}