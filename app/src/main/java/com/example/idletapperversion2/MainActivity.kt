package com.example.idletapperversion2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.idletapperversion2.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    companion object {
        val STATE_TAPS = "tapCount"
        val STATE_TAPPOW = "tapPower"
        val STATE_IDLEPOW = "idlePower"
        val STATE_TAPUPGRADES = "tapUpgrades"
        val STATE_IDLEUPGRADES = "idleUpgrades"
    }

    val TAG = "mainActivity"

    var tapCount = 0
    var tapPower = 1 //amount tapCount increments when tap button is pressed
    var idlePower = 0 //amount tapCount increments every second

    var tapUpgradeLevel = 0
    var idleUpgradeLevel = 0

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //button for user to increment tap count
        val tapButton: Button = binding.tapButton
        tapButton.setOnClickListener {
            tapCount += tapPower
            binding.tapCounter.text = getString(R.string.tap_count, tapCount)
        }

        //button for switching to the upgrade purchase page
        val storeButton: Button = binding.storeButton
        storeButton.setOnClickListener {
            //switch intents to the store page
            val context = storeButton.context
            val intent = Intent(context, StoreActivity::class.java)
            intent.putExtra("taps", tapCount)
            intent.putExtra("tapPower", tapPower)
            intent.putExtra("idlePower", idlePower)
            intent.putExtra("tapUpgrades", tapUpgradeLevel)
            intent.putExtra("idleUpgrades", idleUpgradeLevel)
            context.startActivity(intent)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(STATE_TAPS, tapCount)
            putInt(STATE_TAPPOW, tapPower)
            putInt(STATE_IDLEPOW, idlePower)
            putInt(STATE_TAPUPGRADES, tapUpgradeLevel)
            putInt(STATE_IDLEUPGRADES, idleUpgradeLevel)
        }

    }

    private fun updateUI() {
        binding.tapCounter.text = getString(R.string.tap_count, tapCount)
        binding.tapPowerText.text = getString(R.string.tap_power, tapPower)
        binding.idlePowerText.text = getString(R.string.idle_power, idlePower)
    }

    private fun idleTap() {
        tapCount += idlePower
        binding.tapCounter.text = getString(R.string.tap_count, tapCount)
    }

    override fun onStart() {
        super.onStart()

        tapCount = intent.getIntExtra("taps", tapCount)
        tapPower = intent.getIntExtra("tapPower", tapPower)
        idlePower = intent.getIntExtra("idlePower", idlePower)
        tapUpgradeLevel = intent.getIntExtra("tapUpgrades", tapUpgradeLevel)
        idleUpgradeLevel = intent.getIntExtra("idleUpgrades", idleUpgradeLevel)

        updateUI()
    }
}
