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

class StoreActivity : AppCompatActivity() {

    //You shouldn't really need to define an identical companion object twice. Rather, access it on
    //the activity instance via dot notation.
    companion object {
        const val STATE_TAPS = "tapCount"
        const val STATE_TAPPOW = "tapPower"
        const val STATE_IDLE_POWER = "idlePower" //As in MainActivity
        const val STATE_UPGRADES = "upgrades"
    }

//As in MainActivity
    //flag to prevent overwriting loaded savedInstanceState variables with extras from MainActivity
    private var savedState = false

    var tapCount = 0
    var tapPower = 1
    var idlePower = 0

    //upgrade Levels for buttons
    lateinit var upgrades: IntArray

    private lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storeButtonList = Datasource().loadData()
        //initialize upgrades array based on number of store buttons
        upgrades = IntArray(storeButtonList.size) { 0 }
        binding.recyclerView.adapter = ItemAdapter(this, storeButtonList)


        //button to return to MainActivity
        val returnButton: Button = binding.returnButton
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
                idlePower = getInt(STATE_IDLE_POWER)
                upgrades = getIntArray(STATE_UPGRADES)!!

                savedState = true
            }

        }

        //start function to generate idle taps
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                idleTap()
                handler.postDelayed(this, 1000)//1/second
            }
        }, 0)
    }

    override fun onStart() {
        super.onStart()

        //load extras if they exist and the savedInstanceState wasn't loaded
        if (!savedState && intent.extras != null) {
            tapCount = intent.getIntExtra("taps", tapCount)
            tapPower = intent.getIntExtra("tapPower", tapPower)
            idlePower = intent.getIntExtra("idlePower", idlePower)

            //only load upgrades if list is initialized (length > 0)
            if (intent.getIntArrayExtra("upgrades")!!.isNotEmpty()) {
                upgrades = intent.getIntArrayExtra("upgrades")!!
            }
        }

        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(intent.extras?.getString(STATE_TAPS), tapCount) // See comment above regarding companion objects
            putInt(STATE_TAPPOW, tapPower)
            putInt(STATE_IDLE_POWER, idlePower)
            putIntArray(STATE_UPGRADES, upgrades)
        }

    }

    //function called every second by handler to passively increase tapCount
    private fun idleTap() {
        tapCount += idlePower
        binding.tapCounter.text = getString(R.string.tap_count, tapCount)
    }

    //function to update the UI elements at the top of the screen
    //called by onStart() and ItemAdapter.kt when a button is clicked
    fun updateUI() {
        binding.tapCounter.text = getString(R.string.tap_count, tapCount)
        binding.tapPower.text = getString(R.string.tap_power, tapPower)
        binding.idlePower.text = getString(R.string.idle_power, idlePower)
    }
}