package com.example.idletapperversion2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.idletapperversion2.StoreActivity
import com.example.idletapperversion2.databinding.ListItemBinding
import com.example.idletapperversion2.model.StoreButton
import kotlin.math.roundToInt

class ItemAdapter(
    private val context: Context,
    private val dataset: List<StoreButton>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val store = (context as StoreActivity)

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
        //get the current upgrade cost and set the button text
        updateButtonText(holder, item)
        holder.binding.storeButton.setOnClickListener {
            val upgradeCost = getUpgradeCost(item.baseCost, item.costIncreaseFactor,
                store.upgrades[item.upgradeIndex])
            if (store.tapCount >= upgradeCost) {
                store.tapCount -= upgradeCost
                if(item.idle) {
                    store.idlePower += item.upgradeAmount
                } else {
                    store.tapPower += item.upgradeAmount
                }
                store.upgrades[item.upgradeIndex]++
                updateButtonText(holder, item)
                store.updateTapCount()
            }
        }
    }

    override fun getItemCount() = dataset.size

    private fun updateButtonText(holder: ViewHolder, item: StoreButton) {
        val upgradeCost = getUpgradeCost(item.baseCost, item.costIncreaseFactor,
            store.upgrades[item.upgradeIndex])
        holder.binding.storeButton.text = context.resources.getString(item.stringResourceID,
            store.upgrades[item.upgradeIndex], upgradeCost)
    }

    //determines the costs of an upgrade
    private fun getUpgradeCost(baseCost: Int, costIncreaseFactor: Double, upgradeLevel: Int): Int {
        var cost = baseCost
        var x = 0

        //each upgrade level cost is the previous level cost * costIncreaseFactor
        while(x < upgradeLevel) {
            cost = (cost * costIncreaseFactor).roundToInt()
            x++
        }

        return cost
    }
}