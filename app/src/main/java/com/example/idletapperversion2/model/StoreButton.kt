package com.example.idletapperversion2.model
/*Class for shop buttons used in recyclerview
baseCost: cost for initial upgrade
costIncreaseFactor: amount cost is multiplied by to determine cost of next upgrade
idle: boolean flag for if the upgrade applies to idlePower or not
upgradeAmount: the amount tapPower or idlePower increases whenever the upgrade is purchased
upgradeIndex: index for entry in upgradeLevels array in StoreActivity.kt
 */
class StoreButton (val stringResourceID: Int, val baseCost: Int,
val costIncreaseFactor: Double, val idle: Boolean, val upgradeAmount: Int, var upgradeIndex: Int)