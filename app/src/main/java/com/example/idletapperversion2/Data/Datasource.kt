package com.example.idletapperversion2.Data

import com.example.idletapperversion2.Model.ShopButton

class Datasource {
    fun loadButtons(): List<ShopButton> {
        return listOf<ShopButton>(
            ShopButton(0),
            ShopButton(1)
        )
    }
}