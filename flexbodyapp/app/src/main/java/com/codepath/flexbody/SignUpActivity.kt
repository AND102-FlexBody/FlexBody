package com.codepath.flexbody

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser
import com.parse.ParseException
import com.parse.SignUpCallback

private const val TAG = "SignUpActivity"
class SignUpActivity : AppCompatActivity() {
    private fun openActivityMain() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    private fun signUpUser(username:String, email:String, password:String, age:String){
        Log.i(ContentValues.TAG, "Attempting to sign in user $username");
        val user = ParseUser()
        user.setUsername(username)
        user.setEmail(email)
        user.setPassword(password)
        user.put("Age", "$age")

        user.signUpInBackground { e ->
            if (e == null) {
                openActivityMain()
                finish()
            } else {
                Log.e(TAG, "Problem trying to sign up:", e.cause)
                Toast.makeText(this, "Error Signing up", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val registerButton: Button = findViewById(R.id.registerButton)
        val username: EditText = findViewById(R.id.signUpUsername)
        val password: EditText = findViewById(R.id.signUpPassword)
        val email: EditText = findViewById(R.id.signUpEmail)
        val age: EditText = findViewById(R.id.signUpAge)

        registerButton.setOnClickListener{
            Log.d(ContentValues.TAG, "onClick register Button")
            val thisUsername = username.text.toString()
            val thisEmail = email.text.toString()
            val thisPassword = password.text.toString()
            val thisAge = age.text.toString()
            signUpUser(thisUsername, thisEmail, thisPassword, thisAge)
        }
    }
}