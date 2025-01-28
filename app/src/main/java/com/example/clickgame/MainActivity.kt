package com.example.clickgame

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clickgame.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference.child("jellyCount")

        fetchCount()

        binding.imgButton.setOnClickListener {
            jellyClick()
        }
    }

    private fun fetchCount() {
        database.child("jellyClicker").get().addOnSuccessListener { curr ->
            val count = curr.getValue(Int::class.java) ?: 0
            binding.jellynumbers.text = count.toString()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun jellyClick() {
        val newCount = binding.jellynumbers.text.toString().toInt() + 1
        binding.jellynumbers.text = newCount.toString()

        database.child("jellyClicker").setValue(newCount).addOnSuccessListener {
            Toast.makeText(this, "Update Jelly", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
        }

    }
}