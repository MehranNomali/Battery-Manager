package com.example.batrymanager.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.batrymanager.databinding.ActivitySplashBinding
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)


        var txtArray = arrayOf("Make Your Battery Powerful" ,
            "Make Your Battery Safe" ,
            "Make Your Battery Faster" ,
            "Make Your Battery Powerful")
        for (i in 1..4){
            helpTextGenerator((i * 1000).toLong() , txtArray[i-1])
        }
        Timer().schedule(timerTask {
            startActivity(Intent(this@SplashActivity , MainActivity::class.java))
            finish()
        } , 5000)
    }
    private fun helpTextGenerator(delayTime : Long , helpText : String){
        Timer().schedule(timerTask {
            runOnUiThread(timerTask {
                binding.helpTxt.text = helpText
            })
        } , delayTime)
    }

}