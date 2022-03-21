package com.example.quizheist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.quizheist.R
import com.example.quizheist.databinding.PreviewItemBinding
import com.example.quizheist.model.Result
import java.util.*


class QuizAdapter(private val ls:List<Result>): RecyclerView.Adapter<QuizAdapter.ViewHolder>() {
    var score = 0
    inner class ViewHolder(val b: PreviewItemBinding) : RecyclerView.ViewHolder(b.root) {
    }



    override fun getItemCount(): Int {
        return ls.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PreviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentElement = ls.get(position)
        val r = Random()
        var ans = r.nextInt(4)
        var i = 0
        var j = 0
        var l:Array<String> = Array(4){"n = $it"}
        while(i <= 3){
            if(i == ans){
                l[i] = currentElement.correct_answer.toString()
                j++;
                break
            }else{
                l[i] = currentElement.incorrect_answers!!.get(j).toString()
                i++
                j++
            }
        }
        var answered = false
        holder.b.apply {
            tvQuestion.text = currentElement.question.toString()
            rbOption1.text = l.get(0)
            rbOption2.text = l.get(1)
            rbOption3.text = l.get(2)
            rbOption4.text = l.get(3)
            tvNumber.text = (position+1).toString()

            rg.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
                override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                    if(answered == false) {
                        if (p1 == R.id.rb_option1 && rbOption1.text == currentElement.correct_answer) {
                            score++
                            answered = true
                        }
                    }

                }
            })
        }
    }
}