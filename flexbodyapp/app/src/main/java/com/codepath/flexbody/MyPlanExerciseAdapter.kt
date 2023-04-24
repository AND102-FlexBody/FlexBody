package com.codepath.flexbody

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyPlanExerciseAdapter(val context: Context, private val exercises: List<Exercise>) :
    RecyclerView.Adapter<MyPlanExerciseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.fragment_my_plan_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val tvExerciseName: TextView
        private val ivExerciseImage: ImageView

        init {
            tvExerciseName = itemView.findViewById(R.id.myExerciseName)
            ivExerciseImage = itemView.findViewById(R.id.myExerciseImage)
            itemView.setOnClickListener(this)
        }

        fun bind(exercise: Exercise) {
            tvExerciseName.text = exercise.getName()

            Glide.with(itemView.context).load(exercise.getImage()?.url).into(ivExerciseImage)

        }

        override fun onClick(v: View?) {
            val intentExercise = exercises[absoluteAdapterPosition]
            val intent = Intent(context, MyPlanExerciseDetail::class.java)
            intent.putExtra("name", intentExercise.getName())
            intent.putExtra("desc", intentExercise.getDescription())
            intent.putExtra("image", intentExercise.getImage()?.url)

            context.startActivity(intent)
        }

    }

    companion object {
        const val EXERCISE_EXTRA = "EXERCISE_EXTRA"
    }

}