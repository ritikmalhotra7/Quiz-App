package com.example.quizheist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.quizheist.adapters.QuizMainAdapter
import com.example.quizheist.model.QuizElements
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var rv: QuizMainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)*/
        cl.visibility = View.INVISIBLE
        val quiz_names = listOf<String>("General Knowledge","Music","Films","Sports","Computer Science","Comics","Technology")
        val quiz_number = listOf<Int>(9,12,11,21,18,29,30)
        val quiz_desc = listOf<String>("General Knowledge Desc","Music Desc","Films Desc","Sports Desc","Computer Science Desc","Comics Desc","Technology Desc")
        val quiz_iv = listOf<Int>(R.drawable.gk,R.drawable.music,R.drawable.movies,R.drawable.sports,R.drawable.tech,R.drawable.comics,R.drawable.tech)

        val ls = listOf<QuizElements>(QuizElements(quiz_names[0],quiz_number[0],quiz_desc[0],quiz_iv[0]),
                    QuizElements(quiz_names[1],quiz_number[1],quiz_desc[1],quiz_iv[1]),
                    QuizElements(quiz_names[2],quiz_number[2],quiz_desc[2],quiz_iv[2]),
                    QuizElements(quiz_names[3],quiz_number[3],quiz_desc[3],quiz_iv[3]),
                    QuizElements(quiz_names[4],quiz_number[4],quiz_desc[4],quiz_iv[4]),
                    QuizElements(quiz_names[5],quiz_number[5],quiz_desc[5],quiz_iv[5]),
                    QuizElements(quiz_names[6],quiz_number[6],quiz_desc[6],quiz_iv[6])
            )
        setupRV(ls)

        card1.setOnClickListener {
            BuyingDialog.newInstance(50).show(childFragmentManager,"")
        }
        card2.setOnClickListener {
            BuyingDialog.newInstance(30).show(childFragmentManager,"")
        }
        card3.setOnClickListener {
            BuyingDialog.newInstance(20).show(childFragmentManager,"")
        }

        tvwelcome.text = "WELCOME "+allData().name.toString()
    }
    fun allData():User{
        val u = User()
        Constants.dbReferenceUsers.child(activity?.intent?.getStringExtra("uid").toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)


                u.uid = user!!.uid!!
                Log.d("taget",user.toString())
                u.name = user!!.name.toString()
                u.email = user!!.email.toString()
                u.phone = user!!.phone.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return u
    }

    private fun setupRV(ls:List<QuizElements>){
        rv = QuizMainAdapter(this,ls){
            val bundle = Bundle().apply {
                putInt("quiz_elements",it.number)
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_quizFragment,
                bundle
            )
        }
            rv_quiz.apply {
                adapter = rv
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

                setHasFixedSize(true)
                val helper: SnapHelper = LinearSnapHelper()
                helper.attachToRecyclerView(rv_quiz)
                rv.notifyDataSetChanged()
            }
    }

}