package com.project.moneytracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.project.moneytracker.databinding.ActivitySignupPageBinding

class Signup_Page : AppCompatActivity(){

    private lateinit var binding: ActivitySignupPageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var db: FirebaseFirestore

//Both signIn and saveUserDetails are used to retrieve uid from auth table and map it to user details table in firestore
    //to keep user credentials secure and separate from user_data table
    //--------------------------Start--------------------------------
    private fun signIn(name: String,email: String, password: String) {

        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, retrieve the user UID
                    val user = auth.currentUser
                    user?.let {
                        val uid = it.uid
                        // Now map this UID to your custom user details
                        saveUserDetails(name,uid)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignIn", "signInWithEmail:failure", task.exception)
                }
            }
    auth.signOut()  //to terminate session
    }

    private fun saveUserDetails(name: String,uid: String) {

        //Log.d("Firestore", "Saving user details for UID: $uid")
        val userDetails = hashMapOf(
            "name" to name)

        db.collection("user_data").document(uid)
            .set(userDetails)
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot successfully written!")
                //Toast.makeText( this,"Successfully Saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error writing document", e)
            }
    }

//--------------------------End--------------------------------


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
                            Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()

                            //Below code is used to store name in firebase
                            //By mapping name with uid
                            //user logged in because to retrieve uid using firebaseAuth via mail
                            //and signed out after successful retrieving uid

                            db = FirebaseFirestore.getInstance()
                            signIn(name,mail,pwd)

                            //redirecting to login page
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