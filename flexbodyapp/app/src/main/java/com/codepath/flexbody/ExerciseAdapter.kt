package com.codepath.flexbody

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ExerciseAdapter(
    private val context: Context,
    private val exercises: MutableList<Pair<ExerciseWger, String>>
) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = exercises[position]
        holder.bind(article)
    }

    override fun getItemCount() = exercises.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.exerciseImage)
        private val exerciseNameTextView = itemView.findViewById<TextView>(R.id.exerciseName)
        private val descriptionTextView = itemView.findViewById<TextView>(R.id.exerciseDescription)


        fun bind(exercise: Pair<ExerciseWger, String>) {
            exerciseNameTextView.text = exercise.first.name
            descriptionTextView.text = exercise.second

            if (exercise.first.image != null) {
                Glide.with(context)
                    .load("https://wger.de${exercise.first.image}")
                    .into(mediaImageView)
            }


        }
    }
}



