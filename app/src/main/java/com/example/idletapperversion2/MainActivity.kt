package com.example.idletapperversion2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.idletapperversion2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val STATE_TAPS = "tapCount"
        const val STATE_TAPPOW = "tapPower"
        const val STATE_IDLEPOW = "idlePower"
        const val STATE_UPGRADES = "upgrades"
    }

    val TAG = "mainActivity"

    private var tapCount = 0
    private var tapPower = 1 //amount tapCount increments when tap button is pressed
    private var idlePower = 0 //amount tapCount increments every second

    //array for upgrade levels of all shop items. Used to save values when returning from StoreActivity
    private var upgrades = IntArray(0)

    private lateinit var binding: ActivityMainBinding

    //flag to prevent overwriting loaded savedInstanceState variables with extras from StoreActivity
    private var savedState = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //load the saved instance state if one exists
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                tapCount = getInt(STATE_TAPS)
                tapPower = getInt(STATE_TAPPOW)
                idlePower = getInt(STATE_IDLEPOW)
                upgrades = getIntArray(STATE_UPGRADES)!!

                savedState = true
            }
        }

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
            intent.putExtra("upgrades", upgrades)
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
            putIntArray(STATE_UPGRADES, upgrades)
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

        //load extras if they exist and the savedInstanceState wasn't loaded
        if (!savedState && intent.extras != null) {
            tapCount = intent.getIntExtra("taps", tapCount)
            tapPower = intent.getIntExtra("tapPower", tapPower)
            idlePower = intent.getIntExtra("idlePower", idlePower)
            upgrades = intent.getIntArrayExtra("upgrades")!!
        }

        updateUI()
    }
}
