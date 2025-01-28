package com.example.clickgame

import android.os.Bundle
import android.widget.RemoteViewsService
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clickgame.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var database : FirebaseInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder().baseUrl("https://clickgame-2fe51-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        database = retrofit.create(FirebaseInterface::class.java)

        fetchCount()

        binding.imgButton.setOnClickListener {
            jellyClick()
        }
    }

    private fun fetchCount() {
        database.getCount().enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val count = response.body() ?: 0
                    binding.jellynumbers.text = count.toString()
                } else {
                    Toast.makeText(this@MainActivity, "Failed to connect", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to connect", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun jellyClick() {
        val newCount = binding.jellynumbers.text.toString().toInt() + 1
        binding.jellynumbers.text = newCount.toString()

        database.updateCount(newCount).enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Count Updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Failed to update", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to connect", Toast.LENGTH_SHORT).show()
            }

        })

    }
}