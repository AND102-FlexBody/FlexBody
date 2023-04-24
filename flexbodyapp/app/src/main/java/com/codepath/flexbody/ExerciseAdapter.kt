package com.codepath.flexbody

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.ByteArrayOutputStream

class ExerciseAdapter(
    private val context: Context,
    private val exercises: MutableList<Pair<ExerciseWger, String>>
) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    private val radius = 30
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

        init {
            itemView.findViewById<Button>(R.id.saveExerciseButton).setOnClickListener {
                val name = itemView.findViewById<TextView>(R.id.exerciseName).text.toString()
                val description =
                    itemView.findViewById<TextView>(R.id.exerciseDescription).text.toString()
                val user = ParseUser.getCurrentUser()
                val bitmap = (mediaImageView.drawable as BitmapDrawable).bitmap
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()

                val file = ParseFile("image.png", byteArray)

                submitExercise(name, description, user, file)
            }
        }

        fun bind(exercise: Pair<ExerciseWger, String>) {
            exerciseNameTextView.text = exercise.first.name
            descriptionTextView.text = exercise.second

            if (exercise.first.image != null) {
                Glide.with(context)
                    .load("https://wger.de${exercise.first.image}")
                    .centerCrop()
                    .into(mediaImageView)
            } else {
                Glide.with(context)
                    .load(R.drawable.no_image_filled_25)
                    .centerCrop()
                    .into(mediaImageView)
            }

        }
    }

    private fun submitExercise(
        name: String?,
        description: String,
        user: ParseUser,
        file: ParseFile
    ) {
        val exercise = Exercise()
        if (name != null) {
            exercise.setName(name)
        }
        exercise.setDescription(description)
        exercise.setUser(user)
        exercise.setImage(file)
        exercise.saveInBackground { e ->
            if (e != null) {
                Log.e(TAG, "Error while saving exercise.")
                e.printStackTrace()
                Toast.makeText(
                    context, "Error while saving exercise.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.i(TAG, "Successfully saved exercise.")
                Toast.makeText(
                    context, "Successfully saved exercise.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        const val TAG = "ExerciseAdapter/"
    }
}



