package com.codepath.flexbody

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity  : AppCompatActivity() {

    var handler = Handler().postDelayed({
        val i = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(i)
    }, 1400)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}