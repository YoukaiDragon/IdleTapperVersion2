package com.example.idletapperversion2.model

class StoreButton (val stringResourceID: Int, val baseCost: Int,
val costIncreaseFactor: Double, val idle: Boolean, val upgradeAmount: Int, var upgradeLevel: Int = 0)