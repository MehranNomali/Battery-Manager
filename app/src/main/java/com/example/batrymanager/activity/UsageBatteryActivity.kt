package com.example.batrymanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.batrymanager.R
import com.example.batrymanager.adapter.BatteryUsageAdapter
import com.example.batrymanager.databinding.ActivitySplashBinding
import com.example.batrymanager.databinding.ActivityUsageBatteryBinding
import com.example.batrymanager.model.BatteryModel
import com.example.batrymanager.utils.BatteryUsage
import kotlin.math.roundToInt

class UsageBatteryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsageBatteryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsageBatteryBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        val batteryUsage = BatteryUsage(this)

        val batteryPercentArray: MutableList<BatteryModel> = ArrayList()

        for (item in batteryUsage.getUsageStateList()) {
            if (item.totalTimeInForeground > 0 ){
                val bm = BatteryModel()
                bm.packageName = item.packageName
                bm.percentUsage = (item.totalTimeInForeground.toFloat() / batteryUsage.getTotalTime().toFloat() * 100).toInt()
                batteryPercentArray += bm
            }
        }



        val adapter = BatteryUsageAdapter(this , batteryPercentArray , batteryUsage.getTotalTime())
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }
}