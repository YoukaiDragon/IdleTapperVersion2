package com.example.idletapperversion2.data

import com.example.idletapperversion2.R
import com.example.idletapperversion2.model.StoreButton

class Datasource {
    fun loadData(): List<StoreButton> {
        return listOf<StoreButton>(
            StoreButton(R.string.store1, 10, 1.15, false, 1),
            StoreButton(R.string.store2, 20, 1.25, true, 1),
            StoreButton(R.string.store3, 100, 1.15, false, 5),
            StoreButton(R.string.store4, 200, 1.25, true, 5)
        )
    }
}