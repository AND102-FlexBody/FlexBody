package com.codepath.flexbody

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        this.title = "Profile"

        findViewById<TextView>(R.id.userNameTV).text = ParseUser.getCurrentUser()?.username
        findViewById<TextView>(R.id.emailTV).text = ParseUser.getCurrentUser()?.email

    }
}