package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        Find all views by use of their IDs
        val username = findViewById<EditText>(R.id.username)
        val email =findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val phone = findViewById<EditText>(R.id.phone)
        val signupButton = findViewById<Button>(R.id.signupBtn)
        val signinTextView = findViewById<TextView>(R.id.signintxt)

        // Navigate to Signin activity
        signinTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signin::class.java)
            startActivity(intent)
        }

        // Below when a person clicks on the button, it validates and sends data to api
        signupButton.setOnClickListener {
                // Get values from edit texts
                val nameVal = username.text.toString().trim()
                val emailVal = email.text.toString().trim()
                val passwordVal = password.text.toString().trim()
                val phoneVal = phone.text.toString().trim()

                // Validation logic
                if (nameVal.isEmpty()) {
                    username.error = "Please enter username"
                    username.requestFocus()
                    return@setOnClickListener
                }
                if (emailVal.isEmpty()) {
                    email.error = "Please enter email"
                    email.requestFocus()
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailVal).matches()) {
                    email.error = "Please enter a valid email"
                    email.requestFocus()
                    return@setOnClickListener
                }
                if (passwordVal.isEmpty()) {
                    password.error = "Please enter password"
                    password.requestFocus()
                    return@setOnClickListener
                }
                if (phoneVal.isEmpty()) {
                    phone.error = "Please enter phone number"
                    phone.requestFocus()
                    return@setOnClickListener
                }
                if (!Patterns.PHONE.matcher(phoneVal).matches()) {
                    phone.error = "Please enter a valid phone number"
                    phone.requestFocus()
                    return@setOnClickListener
                }

                // If all validations pass, proceed with API call
                val api = "https://victoria.alwaysdata.net/api/signup"
                val data = RequestParams()
                data.put("username", nameVal)
                data.put("email", emailVal)
                data.put("password", passwordVal)
                data.put("phone", phoneVal)

            //import of the API helper class
            val helper = ApiHelper(applicationContext)

            // Inside of the helper class, access the function post
            helper.post(api, data)

            email.text.clear()
            password.text.clear()
            phone.text.clear()
            username.text.clear()

            // intent to the main activity page
            // val intent = Intent(applicationContext, MainActivity::class.java)
            // startActivity(intent)
        }
    }
}