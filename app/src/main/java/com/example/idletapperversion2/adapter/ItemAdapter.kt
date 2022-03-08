package com.example.idletapperversion2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.idletapperversion2.R
import com.example.idletapperversion2.databinding.ListItemBinding
import com.example.idletapperversion2.model.StoreText

class ItemAdapter(
    private val context: Context,
    private val dataset: List<StoreText>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.itemTitle.text = context.resources.getString(item.stringResourceID)
        //holder.binding.textView.text = context.resources.getString(item.stringResourceID)
    }


    override fun getItemCount() = dataset.size
}