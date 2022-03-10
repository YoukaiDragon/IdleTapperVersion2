package com.example.idletapperversion2.data

import com.example.idletapperversion2.R
import com.example.idletapperversion2.model.StoreButton

class Datasource {
    fun loadData(): List<StoreButton> {
        return listOf<StoreButton>(
            StoreButton(R.string.tapper, 10, 1.15, false, 1, 0),
            StoreButton(R.string.super_tapper, 100, 1.15, false, 5, 1),
            StoreButton(R.string.ultra_tapper, 1000, 1.35, false, 20, 2),
            StoreButton(R.string.idler, 20, 1.25, true, 1, 3),
            StoreButton(R.string.super_idler, 200, 1.25, true, 5, 4),
            StoreButton(R.string.ultra_idler, 2000, 1.5, true, 20, 5)
        )
    }
}