package com.example.quizheist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quizheist.Constants.Companion.firebaseAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val mAuth = firebaseAuth.currentUser
        if (mAuth != null) {
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("email",mAuth.email.toString())
            i.putExtra("uid",mAuth.uid.toString())
            Log.d("taget",mAuth.email+mAuth.uid+"")
            startActivity(i)
            finish()
            Log.d("taget", mAuth.uid)
        } else {
            Log.d("taget","null")
            super.onStart()
        }
    }
    override fun onStart() {
        super.onStart()

    }
}