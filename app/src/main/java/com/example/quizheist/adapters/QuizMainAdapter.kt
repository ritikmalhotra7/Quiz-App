package com.example.quizheist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import com.example.quizheist.R
import com.example.quizheist.databinding.QuizListBinding
import com.example.quizheist.model.Questions
import com.example.quizheist.model.QuizElements

class QuizMainAdapter(val frag : Fragment, val ls:List<QuizElements>,val listener :(QuizElements) -> Unit): RecyclerView.Adapter<QuizMainAdapter.ViewHolder>() {

    inner class ViewHolder(val b:QuizListBinding) :RecyclerView.ViewHolder(b.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            QuizListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentElement = ls.get(position)
        holder.b.apply {
            tvName.text = currentElement.name
            tvDescription.text = currentElement.desc
            ivBg.load(currentElement.image){
                transformations(BlurTransformation(holder.b.root.context,10f))
            }
            /*setOnClickListener{
                Log.d("taget",currentElement.name.toString())
                onItemClickListener?.let {
                    it(currentElement)
                }
            }*/
            this.root.setOnClickListener {
                listener(currentElement)
            }
        }
    }


    override fun getItemCount(): Int {
        return ls.size
    }
}