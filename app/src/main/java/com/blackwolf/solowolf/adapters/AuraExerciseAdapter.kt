package com.blackwolf.solowolf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blackwolf.solowolf.R

class AuraExerciseAdapter(
    private val exercises: List<AuraExercise>,
    private val onItemClick: (AuraExercise) -> Unit
) : RecyclerView.Adapter<AuraExerciseAdapter.AuraExerciseViewHolder>() {

    inner class AuraExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvExerciseTitle)
        
        fun bind(exercise: AuraExercise) {
            tvTitle.text = exercise.title
            itemView.setOnClickListener { onItemClick(exercise) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuraExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aura_exercise, parent, false)
        return AuraExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: AuraExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount(): Int = exercises.size
}
