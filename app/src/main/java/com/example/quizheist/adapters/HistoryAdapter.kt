package com.example.quizheist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import com.example.quizheist.databinding.HistoryPreviewItemBinding
import com.example.quizheist.databinding.QuizListBinding
import com.example.quizheist.model.HistoryElements
import com.example.quizheist.model.QuizElements

class HistoryAdapter(val ls:List<HistoryElements>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val b: HistoryPreviewItemBinding) : RecyclerView.ViewHolder(b.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HistoryPreviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentElement = ls.get(position)
        holder.b.apply {
            tvScore.text = "${currentElement.score*10}%"
            tvTypes.text = currentElement.type
            sbProgress.progress = currentElement.score
            tvDate.text = currentElement.date
        }
    }


    override fun getItemCount(): Int {
        return ls.size
    }
}