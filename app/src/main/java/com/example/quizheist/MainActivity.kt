package com.example.quizheist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import androidx.navigation.ui.setupWithNavController
import com.example.quizheist.databinding.ActivityMainBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding : ActivityMainBinding get() = _binding!!
    val repository :MainRepository = MainRepository()
    val viewModelFactory = ViewModelFactory(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       /* viewModel = ViewModelProvider(this,viewModelFactory)[MainviewModel::class.java]*/
        bottomNavigationView.background = null
        bottomNavigationView.menu.get(1).isEnabled = false
        bottomNavigationView.setupWithNavController(mainNavHostFragment.findNavController())
        fab.setOnClickListener {
            mainNavHostFragment.findNavController().navigate(R.id.historyFragment)
        }
        mainNavHostFragment.findNavController().addOnDestinationChangedListener{_,dest,_ ->
            if(dest.id == R.id.quizFragment){
                bottomAppBar.visibility = View.GONE
                fab.visibility = View.GONE
            }else{
                fab.visibility = View.VISIBLE
                bottomAppBar.visibility = View.VISIBLE
            }
        }
    }



}