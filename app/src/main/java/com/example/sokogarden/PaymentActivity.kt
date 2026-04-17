package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        find views by use of the ids
        val txtname = findViewById<TextView>(R.id.txtProductName)
        val txtcost =  findViewById<TextView>(R.id.txtProductCost)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)

        // Retrieve the data passed from the preview Activity (ProductAdapter)
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost",0)
        val product_photo = intent.getStringExtra("product_photo")

        //Update the textview with the data passed rom the previous Activity
        txtname.text = name
        txtcost.text = "Ksh $cost"

        //Specify the image URL
        val imageUrl = "https://victoria.alwaysdata.net/static/images/$product_photo"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imgProduct)

        //Find the edittext and the paynow button by use of there IDs
        val phone = findViewById<EditText>(R.id.phone)
        val btnPay = findViewById<Button>(R.id.pay)

        //set click listener on the button
        btnPay.setOnClickListener {
            //specify the API endpoint for making payment
            val api = "https://victoria.alwaysdata.net/api/mpesa_payment"
            //create a requestParams
            val data = RequestParams()

            //Insert data into the RequestParams
            data.put("amount",cost)
            data.put("phone", phone.text.toString().trim())

            //Import the helper class
            val helper = ApiHelper(applicationContext)

            //access the post function inside of the helper class
            helper.post(api,data)

            //clear the phone number from the editText
            phone.text.clear()
        }
    }
}