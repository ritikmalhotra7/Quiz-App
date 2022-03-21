package com.example.quizheist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizheist.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        /*ActivitySplashScreenBinding.inflate(layoutInflater).animationView.scaleType = ""*/
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        } ;thread.start ()

        ActivitySplashScreenBinding.inflate(layoutInflater).animationView.pauseAnimation()
    }
}