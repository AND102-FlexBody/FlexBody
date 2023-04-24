package com.codepath.flexbody

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser
import kotlin.math.sign

private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    private fun openActivityMain() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    private fun loginUser(username: String, password:String) {
        Log.i(TAG, "Attempting to login user $username")

        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (e != null) {
                //hardcied login
                openActivityMain()
                finish()
                Log.e(TAG, "Problem trying to Login ", e)
                Toast.makeText(this, "Error Logging in", Toast.LENGTH_SHORT).show()
            }


            else {
                openActivityMain()
                finish()
            }})
        )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.getSupportActionBar()?.hide()
        setContentView(R.layout.activity_login)

        if (ParseUser.getCurrentUser() != null) {
            openActivityMain()
        }

        val signUpButton: Button = findViewById(R.id.textViewSignUp)
        val username: EditText = findViewById(R.id.editTextUsername)
        val password: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.buttonSignIn)

        loginButton.setOnClickListener {
            Log.d(TAG, "onClick login Button")
            val thisUsername = username.text.toString()
            val thisPassword = password.text.toString()
            loginUser(thisUsername, thisPassword)
        }

        signUpButton.setOnClickListener{
            val i = Intent(this, SignUpActivity::class.java)
            this.startActivity(i)
            finish()
        }
    }
}