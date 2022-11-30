package com.example.databaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels

// Android UI basics - done
// Kotlin Basics- done
// Android Basics (activities, logic, viewmodels, adapters etc...) - done

// GIT basics - done

// Android UI Advanced - 75%
// Kotlin Advanced - 65%
// Android Advanced - 65%

// Algorithms
// problem solving skills - example -< Implement a function that returns the biggest number in an array of integers

 

// Resume and job interviews

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel: MainViewModel by viewModels()
        val button = findViewById<Button>(R.id.submit_button)
        val firstName = findViewById<EditText>(R.id.first_name)
        val lastName = findViewById<EditText>(R.id.last_name)
        val getUsersButton = findViewById<Button>(R.id.get_users_button)

        mainViewModel.userListLiveData.observe(this) {

        }

        button.setOnClickListener {
            val user = User(System.currentTimeMillis(), firstName.text.toString(), lastName.text.toString())
            mainViewModel.addUserToDatabase(user, this)
        }

        getUsersButton.setOnClickListener(){
            mainViewModel.getUsersFromDataBase(this)
            val myIntent = Intent(this, UserListActivity::class.java)
            startActivity(myIntent)
        }
    }
}