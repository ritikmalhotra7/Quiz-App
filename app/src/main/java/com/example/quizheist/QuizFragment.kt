package com.example.quizheist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.quizheist.Constants.Companion.dbReferenceHistory
import com.example.quizheist.Constants.Companion.getDate
import com.example.quizheist.Constants.Companion.getTime
import com.example.quizheist.Constants.Companion.quiz_name
import com.example.quizheist.Constants.Companion.quiz_numbers
import com.example.quizheist.adapters.QuizAdapter
import com.example.quizheist.adapters.QuizMainAdapter
import com.example.quizheist.databinding.ActivityMainBinding
import com.example.quizheist.databinding.FragmentQuizBinding
import com.example.quizheist.model.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.util.*

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private var _binding: FragmentQuizBinding? = null
    val binding : FragmentQuizBinding get() = _binding!!
    private lateinit var ls1: List<Result>
    private lateinit var rv: QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizBinding.inflate(layoutInflater)
        ls1 = arrayListOf()
        show()
        val viewModel = ViewModelProvider(this,ViewModelFactory(MainRepository())).get(MainviewModel::class.java)
        viewModel.getQuizQuestions(arguments?.getInt("quiz_elements")!!)
        viewModel.quizList.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resources.Success ->{
                    response.data?.let {
                        Log.d("taget",it.results?.size.toString())
                        ls1 = it.results!!
                        setupRv()
                        rv.notifyDataSetChanged()
                        hide()
                        Log.d("tag",it.results?.size.toString())
                    }
                }
                is Resources.Error ->{
                    response.data?.let{
                        hide()
                        Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resources.Loading ->{
                    show()
                }
            }
        })
        submit.setOnClickListener {
            show()
            val date = getDate()
            val time = getTime()
            var type = arguments?.getInt("quiz_elements").toString()
            var typs = ""
            for(i in quiz_numbers.indices){
                if(quiz_numbers[i] == type.toInt()){
                    typs = quiz_name[i]
                }
            }
            dbReferenceHistory.child(activity?.intent?.getStringExtra("uid").toString()).child(UUID.randomUUID().toString()).setValue(HistoryElements(rv.score,typs,date,
                time)).addOnSuccessListener {
                    hide()
                    Toast.makeText(activity,"your score is :"+ rv.score,Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.mainFragment)
            }.addOnFailureListener{
                hide()
                Toast.makeText(activity,"Your score is not been stored as of some error!",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.mainFragment)

            }
        }
    }
    fun setupRv(){
        rv = QuizAdapter(ls1)
        quiz_rv.adapter = rv
        quiz_rv.layoutManager = LinearLayoutManager(activity)
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(rv_quiz)
    }
    fun show(){
        cl_quiz.visibility = View.VISIBLE
    }
    fun hide(){
        cl_quiz.visibility = View.INVISIBLE
    }

}