package com.project.moneytracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.project.moneytracker.databinding.ActivityLoginPageBinding

class Login_Page : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()

        binding.Signup.setOnClickListener {
            //this will redirect to signup page
            val intent = Intent(this, Signup_Page::class.java)
            startActivity(intent)
        }
        enableEdgeToEdge()

        binding.LoginButton.setOnClickListener {
            val mail = binding.editTextTextEmailAddress.text.toString()
            val pwd = binding.editTextTextPassword.text.toString()

            if (mail.isNotEmpty() && pwd.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener {
                    if (it.isSuccessful) {            //this will redirect to Login page

                        val intent = Intent(this, Dashboard::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show()
            }
        }


        /*setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button = findViewById<Button>(R.id.Signup_button)
        button.setOnClickListener()
        {
            val intent= Intent(this,Signup_Page::class.java)
            startActivity(intent)
        }*/
    }
}