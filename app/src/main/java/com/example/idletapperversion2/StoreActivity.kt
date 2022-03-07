package com.example.idletapperversion2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import com.example.idletapperversion2.databinding.ActivityMainBinding
import com.example.idletapperversion2.databinding.ActivityStoreBinding
import kotlin.math.pow
import kotlin.math.roundToInt

class StoreActivity : AppCompatActivity() {

    val TAG = "storeActivity"

    var tapCount = 0
    var tapPower = 1
    var idlePower = 0

    //base factors for determining upgrade cost
    val baseUpgradeCost = 10
    val costIncreaseFactor = 1.15

    //upgrade Levels for buttons

    lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val returnButton : Button = binding.returnButton
        returnButton.setOnClickListener {
            val context = returnButton.context
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("taps", tapCount)
            intent.putExtra("tapPower", tapPower)
            intent.putExtra("idlePower", idlePower)
            context.startActivity(intent)
        }

        //button for user to increase number of taps gained per click of tapButton
        val powerUpgradeButton: Button = binding.upgradeTap
        powerUpgradeButton.setOnClickListener {
            //spend taps to upgrade tapPower if the user has enough
            val powerUpgradeCost = getUpgradeCost(baseUpgradeCost, costIncreaseFactor, idlePower - 1)
            if (tapCount >= powerUpgradeCost) {
                tapCount -= powerUpgradeCost
                tapPower += 1
                //update the display
                updateUI()
            }
        }

        //button for user to increase number of taps gained passively each second
        val idleUpgradeButton: Button = binding.upgradeIdle
        idleUpgradeButton.setOnClickListener {
            //spend taps to upgrade idleTap if the user has enough
            val idleUpgradeCost =  getUpgradeCost(2 * baseUpgradeCost, costIncreaseFactor, idlePower)
            if (tapCount >= idleUpgradeCost) {
                tapCount -= idleUpgradeCost
                idlePower += 1
                //update the display
                updateUI()
            }
        }

        //start function to generate idle taps
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                idleTap()
                handler.postDelayed(this, 1000)//1 sec delay
            }
        }, 0)
    }

    override fun onStart() {
        super.onStart()
        tapCount = intent.getIntExtra("taps", tapCount)
        tapPower = intent.getIntExtra("tapPower", tapPower)
        idlePower = intent.getIntExtra("idlePower", idlePower)

        updateUI()
    }

    private fun idleTap() {
        tapCount += idlePower
    }

    private fun updateUI() {
        binding.upgradeTap.text = getString(R.string.upgrade_tap,
            getUpgradeCost(baseUpgradeCost, costIncreaseFactor, tapPower - 1))
        binding.upgradeIdle.text = getString(R.string.upgrade_idle,
            getUpgradeCost(2 * baseUpgradeCost, costIncreaseFactor, idlePower))
    }

    //determines the costs of an upgrade
    private fun getUpgradeCost(baseCost: Int, costIncreaseFactor: Double, upgradeLevel: Int): Int {
        var cost = baseCost
        var x = 0

        while(x < upgradeLevel) {
            cost = (cost * costIncreaseFactor).roundToInt()
            x++
        }

        return cost
    }
}