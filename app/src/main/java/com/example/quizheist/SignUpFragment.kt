package com.example.quizheist

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.quizheist.Constants.Companion.dbReferenceUsers
import com.example.quizheist.Constants.Companion.firebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.cl
import kotlinx.android.synthetic.main.fragment_sign_up.password

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hide()
        signupbutton.setOnClickListener {
            show()
            name.isEnabled = false
            password.isEnabled = false
            emailid.isEnabled = false
            viaEmail()
        }
    }
    private fun viaEmail() {
        val names = name.text.toString()
        val emails = emailid.text.toString()
        val passwords = password.text.toString()
        if(!TextUtils.isEmpty(emailid.text.toString()) && !TextUtils.isEmpty(password.text.toString())){
            firebaseAuth.createUserWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        checkEmail(emails)
                    } else {
                        hide()
                        Log.d("taget",task.exception.toString())
                        Toast.makeText(
                            context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener {
                    hide()
                    name.isEnabled = true
                    password.isEnabled = true
                    emailid.isEnabled = true
                }
        }
        else
        {
            hide()
            Toast.makeText(activity , "Please Enter Something", Toast.LENGTH_SHORT).show()
        }
    }
    private fun addUserToDbViaEmail(name : String, email :String, uid : String) {
        dbReferenceUsers = FirebaseDatabase.getInstance()
            .reference
        dbReferenceUsers.child("user").child(uid).setValue(User(name,null,email,uid))
    }
    private fun checkEmail(email:String)
    {
        val firebaseUser = firebaseAuth.currentUser
        val name = name.text.toString().trim()
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(activity,"Verification mail sent", Toast.LENGTH_SHORT).show()
                addUserToDbViaEmail(name,email, firebaseAuth.currentUser!!.uid)
                firebaseAuth.signOut()
                hide()
                val intent = Intent(activity,LoginActivity::class.java)
                startActivity(intent)

            }else{
                hide()
                Toast.makeText(activity,"error occured", Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun show(){
        cl.visibility = View.VISIBLE
    }
    fun hide(){
        cl.visibility = View.INVISIBLE
    }

}