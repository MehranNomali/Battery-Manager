package com.example.batrymanager.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.example.batrymanager.R
import com.example.batrymanager.model.BatteryModel
import com.example.batrymanager.utils.BatteryUsage
import com.example.batrymanager.databinding.ActivityMainBinding
import com.example.batrymanager.service.BatteryAlarmService
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        startService()
        binding.imgMenu.setOnClickListener {
            binding.drawer.openDrawer(Gravity.RIGHT)
        }
        binding.incDrawer.txtAppUsage.setOnClickListener {
            startActivity(Intent(this@MainActivity, UsageBatteryActivity::class.java))
            binding.drawer.closeDrawer(Gravity.RIGHT)

        }

        registerReceiver(batteryInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private fun startService(){
        val serviceIntent = Intent(this , BatteryAlarmService::class.java)
        ContextCompat.startForegroundService(this , serviceIntent)
    }

    private var batteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

            if (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) == 0) {
                binding.textPlug.text = "plug-out"
            } else {
                binding.textPlug.text = "plug-in"
            }
            binding.textTemp.text =
                (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10).toString() + " "
            binding.textVoltage.text =
                (intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) / 1000).toString() + " volt"
            binding.textTechnology.text = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)

            binding.circularProgressBar.progressMax = 100f
            binding.circularProgressBar.setProgressWithAnimation(batteryLevel.toFloat())
            binding.txtCharge.text = batteryLevel.toString() + " %"

            var health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
            when (health) {
                BatteryManager.BATTERY_HEALTH_DEAD -> {
                    binding.txtHealth.text =
                        "your battery is fully dead , please change your battery"
                    binding.txtHealth.setTextColor(Color.BLACK)
                    binding.imgHealth.setImageResource(R.drawable.health_dead)
                }

                BatteryManager.BATTERY_HEALTH_COLD -> {
                    binding.txtHealth.text =
                        "your battery is cold , it's ok "
                    binding.txtHealth.setTextColor(Color.BLUE)
                    binding.imgHealth.setImageResource(R.drawable.snow1)
                }

                BatteryManager.BATTERY_HEALTH_GOOD -> {
                    binding.txtHealth.text =
                        "your battery is good , please take care of that"
                    binding.txtHealth.setTextColor(Color.LTGRAY)
                    binding.imgHealth.setImageResource(R.drawable.like)
                }

                BatteryManager.BATTERY_HEALTH_OVERHEAT -> {
                    binding.txtHealth.text =
                        "your battery is overheat , please don't work with your phone"
                    binding.txtHealth.setTextColor(Color.RED)
                    binding.imgHealth.setImageResource(R.drawable.hot2)
                }

                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> {
                    binding.txtHealth.text =
                        "your battery is over voltage , please don't work with your phone"
                    binding.txtHealth.setTextColor(Color.YELLOW)
                    binding.imgHealth.setImageResource(R.drawable.health_volt)
                }
            }


        }
    }
}
