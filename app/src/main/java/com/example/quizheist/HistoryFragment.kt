package com.example.quizheist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizheist.Constants.Companion.addDataToFirebaseHistory
import com.example.quizheist.Constants.Companion.dbReferenceHistory
import com.example.quizheist.Constants.Companion.firebaseAuth
import com.example.quizheist.adapters.HistoryAdapter
import com.example.quizheist.databinding.FragmentHistoryBinding
import com.example.quizheist.model.HistoryElements
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_main.*

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var rview: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ls = arrayListOf<HistoryElements>();
        setupRV(ls)
        dbReferenceHistory.child(firebaseAuth.currentUser!!.uid).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children){
                    val hs = snap.getValue(HistoryElements::class.java)
                    ls.add(hs!!)
                    setupRV(ls)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun setupRV(ls:List<HistoryElements>){
        rview = HistoryAdapter(ls)
        rv_history.apply {
            adapter = rview
            layoutManager = LinearLayoutManager(activity)
        }
        rview.notifyDataSetChanged()
    }



}