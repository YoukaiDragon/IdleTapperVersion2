package com.example.idletapperversion2.data

import com.example.idletapperversion2.R
import com.example.idletapperversion2.model.StoreText

class Datasource {
    fun loadData(): List<StoreText> {
        return listOf<StoreText>(
            StoreText(R.string.testing1),
            StoreText(R.string.testing2)
        )
    }
}