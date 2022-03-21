package com.example.quizheist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizheist.Constants.Companion.firebaseAuth
import com.example.quizheist.databinding.FragmentSettingBinding
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(activity,LoginActivity::class.java))
        }
        privacy.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_privacyPolicyFragment)
        }
        terms.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_termsFragment)
        }
        share.setOnClickListener {
            var url = ""
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this awesome Quiz App : "+url)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent,"Share with :"))
        }
        chat.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_chatFragment)
        }
    }


}