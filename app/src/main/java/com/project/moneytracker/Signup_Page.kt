package com.project.moneytracker

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.project.moneytracker.databinding.ActivitySignupPageBinding

class Signup_Page : AppCompatActivity(){

    private lateinit var binding: ActivitySignupPageBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_signup_page)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        //binding code starts

        binding= ActivitySignupPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding code ends

        //already user code starts
        binding.alreadyUser.setOnClickListener{
            //this will redirect to Login page

            val intent= Intent(this,Login_Page::class.java)
            startActivity(intent)
        }
        //already user code ends

        //firebase auth
        firebaseAuth= FirebaseAuth.getInstance()
        binding.dataRelayButton.setOnClickListener(){
            val name=binding.editTextText.text.toString()
            val mail=binding.editTextTextEmailAddress2.text.toString()
            val pwd=binding.pass.text.toString()
            val cnfpwd=binding.cnpass.text.toString()

            if (name.isNotEmpty() && mail.isNotEmpty() && pwd.isNotEmpty() && cnfpwd.isNotEmpty())
            {
                if (pwd==cnfpwd)
                {
                    firebaseAuth.createUserWithEmailAndPassword(mail,pwd).addOnCompleteListener{
                        if (it.isSuccessful)
                        {            //this will redirect to Login page
                            val intent= Intent(this,Login_Page::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this,  it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                }
                else
                {
                    Toast.makeText(this, "Password not Matching", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show()
            }
        }

        /*val button = findViewById<Button>(R.id.data_relay_button)
        button.setOnClickListener()
        {
            val intent= Intent(this,Login_Page::class.java)
            startActivity(intent)
        }*/
    }

}