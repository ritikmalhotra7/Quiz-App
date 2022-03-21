package com.example.quizheist


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.quizheist.Constants.Companion.firebaseAuth
import com.example.quizheist.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var mAuth:FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = firebaseAuth
        hide()
        signUpButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        loginPhone.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_loginViaPhoneFragment)
        }
        loginButton.setOnClickListener {
            show()
            if (!TextUtils.isEmpty(email.text.toString()) && !TextUtils.isEmpty(password.text.toString())) {
                verifyEmail(email.text.toString(), password.text.toString())
            }else {
                hide()
                Toast.makeText(activity, "Please Enter Something", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                hide()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            } else {
                hide()
                Toast.makeText(activity, " Authentication Failed:"+task.exception, Toast.LENGTH_SHORT).show()
                Log.d("error",task.exception.toString())
            }
        }
    }
    private fun verifyEmail(email: String, password: String) {
           try{

               mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                   if(it.user != null){
                       Log.d("error",it.toString())
                   }else{
                       Log.d("error","is null")
                   }
                   if (it.user!!.isEmailVerified) {
                       hide()
                       login(email, password)
                   } else {
                       hide()
                       Toast.makeText(activity, "Please Verify", Toast.LENGTH_SHORT).show()
                   }
                   mAuth.signOut()
               }.addOnFailureListener {
                   hide()
                   Toast.makeText(activity, " Authentication Failed! May be User doesn't exist", Toast.LENGTH_SHORT).show()
                   Log.d("error",it.toString())
               }
           }catch (e:Exception){
               hide()
               e.printStackTrace()
           }
    }

    override fun onStart() {
        super.onStart()
        val mAuth1 = firebaseAuth.currentUser
        if (mAuth1 != null) {
            startActivity(Intent(activity, MainActivity::class.java))
        } else {
            super.onStart()
        }
    }
    fun show(){
        cl.visibility = View.VISIBLE
    }
    fun hide(){
       cl.visibility = View.INVISIBLE
    }
}