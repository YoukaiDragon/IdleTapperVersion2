package com.example.idletapperversion2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.idletapperversion2.adapter.ItemAdapter
import com.example.idletapperversion2.data.Datasource
import com.example.idletapperversion2.databinding.ActivityStoreBinding
import kotlin.math.roundToInt

class StoreActivity : AppCompatActivity() {

    companion object {
        const val STATE_TAPS = "tapCount"
        const val STATE_TAPPOW = "tapPower"
        const val STATE_IDLEPOW = "idlePower"
        const val STATE_UPGRADES = "upgrades"
    }

    val TAG = "storeActivity"

    //flag to prevent overwriting loaded bundle varibles with extras from MainActivity
    private var loadedBundle = false

    var tapCount = 0
    var tapPower = 1
    var idlePower = 0

    //base factors for determining upgrade cost
    private val baseUpgradeCost = 10
    private val costIncreaseFactor = 1.15

    //upgrade Levels for buttons
    lateinit var upgrades: IntArray

    private lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storeButtonList = Datasource().loadData()
        upgrades = IntArray(storeButtonList.size){0}
        binding.recyclerView.adapter = ItemAdapter(this, storeButtonList)


        val returnButton : Button = binding.returnButton
        returnButton.setOnClickListener {
            val context = returnButton.context
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("taps", tapCount)
            intent.putExtra("tapPower", tapPower)
            intent.putExtra("idlePower", idlePower)
            intent.putExtra("upgrades", upgrades)
            context.startActivity(intent)
        }

        //load the saved instance state if one exists
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                tapCount = getInt(STATE_TAPS)
                tapPower = getInt(STATE_TAPPOW)
                idlePower = getInt(STATE_IDLEPOW)
                upgrades = getIntArray(STATE_UPGRADES)!!

                loadedBundle = true
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
        //if statement to prevent the contents of the bundle from getting overwritten
        //if they were loaded in
        if(!loadedBundle && intent.extras != null) {
            tapCount = intent.getIntExtra("taps", tapCount)
            tapPower = intent.getIntExtra("tapPower", tapPower)
            idlePower = intent.getIntExtra("idlePower", idlePower)

            //only load upgrades if list is initialized (length > 0)
            if(intent.getIntArrayExtra("upgrades")!!.isNotEmpty()) {
                upgrades = intent.getIntArrayExtra("upgrades")!!
            }
        }

        updateTapCount()
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

    private fun idleTap() {
        tapCount += idlePower
        binding.tapCounter.text = getString(R.string.tap_count, tapCount)
    }

    fun updateTapCount() {
        binding.tapCounter.text = getString(R.string.tap_count, tapCount)
    }
}